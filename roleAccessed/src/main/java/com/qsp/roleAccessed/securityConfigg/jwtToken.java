package com.qsp.roleAccessed.securityConfigg;

import java.security.Key;
import java.util.*;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class jwtToken {

    // ✅ Strong Secret Key (minimum 32 characters)
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(
            "mysecretkey123456mysecretkey123456".getBytes()
    );

    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 1 day

    // ✅ GENERATE TOKEN
    public String generateToken(UserDetails userDetails) {

        List<String> roles = new ArrayList<>();

        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.getAuthority());
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ EXTRACT USERNAME
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ✅ EXTRACT ROLES
    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    // ✅ VALIDATE TOKEN
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    // ✅ CHECK EXPIRATION
    private boolean isExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // ✅ EXTRACT CLAIMS (UPDATED parserBuilder)
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ PASSWORD ENCODER BEAN
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}