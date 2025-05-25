package com.example.BTL.configuration;

import com.example.BTL.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {"/user/register", "/auth/log-in" };
    private final String[] PUBLIC_ENDPOINTS2 = {"/user/register", "/user/login"};

    @Autowired
    private JwtFilter jwtFilter; // Thêm JwtFilter

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS2).permitAll()
                        .requestMatchers(HttpMethod.GET, "/home/account").permitAll()
                        // tenant
                        .requestMatchers(HttpMethod.GET, "/home/view-schedule").permitAll()
                        // landlord
                        .requestMatchers(HttpMethod.GET, "/home/addRoom").permitAll()
                        .requestMatchers(HttpMethod.GET, "/home/list-room").permitAll()
                        .requestMatchers(HttpMethod.GET, "/home/manager-schedule").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/rooms/create_room").hasRole("LANDLORD")
                        // admin
                        .requestMatchers(HttpMethod.GET, "/home/manager-user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/home/manager-room").permitAll()
                        
                        .requestMatchers(HttpMethod.GET, "/home/index").permitAll()
                        .requestMatchers(HttpMethod.GET, "/*.html").permitAll()
                        .requestMatchers(
                            "/room-detail.html",
                            "/room-detail.css",
                            "/css/**",
                            "/js/**",
                            "/images/**",
                            "/static/**",
                            "/favicon.ico"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Không dùng session
                .and()
                .formLogin().disable()
                .httpBasic().disable();


//        // Thêm JwtFilter trước UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }
}
