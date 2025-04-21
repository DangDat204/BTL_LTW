package com.example.BTL.service;

import com.example.BTL.configuration.JwtUtil;
import com.example.BTL.entity.User;
import com.example.BTL.exception.AppException;
import com.example.BTL.exception.ErrorCode;
import com.example.BTL.model.request.Authentication.AuthenticationRequest;
import com.example.BTL.model.response.Authentication.AuthenticationResponse;
import com.example.BTL.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.StringJoiner;


@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Xác thực bằng AuthenticationManager
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch(BadCredentialsException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Tạo token
        String token = null;
        try {
            token = jwtUtil.generateToken(user.getUsername(), buildScope(user));
            System.out.println(token);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String buildScope(User user){
        StringJoiner scope = new StringJoiner(" ");
//        Nếu roles không rỗng
        if (user.getRole() != null) {
            var role = user.getRole();
            scope.add("ROLE_" + role.getRoleName());
        }
        return scope.toString().trim();
    }
}
