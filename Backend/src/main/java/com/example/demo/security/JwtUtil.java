package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ✅ Secret key
    private final String SECRET =
            "mysecretkeymysecretkeymysecretkey123";

    // ✅ Generate signing key
    private Key getSignKey() {

        return Keys.hmacShaKeyFor(
                SECRET.getBytes()
        );
    }

    // ✅ Generate JWT token
    public String generateToken(
            String username,
            String role) {

        return Jwts.builder()

                .setSubject(username)

                .claim("role", role)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )

                .signWith(
                        getSignKey(),
                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    // ✅ Extract all claims
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(getSignKey())

                .build()

                .parseClaimsJws(token)

                .getBody();
    }

    // ✅ Extract username
    public String extractUsername(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    // ✅ Extract role
    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }
}