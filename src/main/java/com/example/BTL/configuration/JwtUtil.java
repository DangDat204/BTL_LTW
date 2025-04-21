package com.example.BTL.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    @Value("${jwt.signerKey}")
    private String signerKey;
    private SecretKey key;
    private final long EXPIRATION_TIME = 3600000; // 1 giờ (có thể thay đổi)

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(signerKey.getBytes(StandardCharsets.UTF_8));
        System.out.println("signerKey: " + signerKey);
        System.out.println("Length in characters: " + signerKey.length());
        System.out.println("Length in bytes: " + signerKey.getBytes().length);
    }

    // Tạo token
    public String generateToken(String username, String roles) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("DangTienDatDz.com")
                .claim("authorities", List.of(roles)) // Lưu scope hoặc roles
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key) // Dùng SecretKey
                .compact();
    }

    // Lấy username từ token
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // Lấy scope/roles từ token
    public List<String> getAuthoritiesFromToken(String token) {
        List<String> authorities = (List<String>) getClaimsFromToken(token).get("authorities");
        return authorities;
    }

    // Lấy claims từ token
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Kiểm tra token hợp lệ
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key) // Dùng SecretKey thay vì signerKey
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("Token validation error: " + e.getMessage());
            return false;
        }
    }
}
