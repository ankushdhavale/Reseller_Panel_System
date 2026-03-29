package com.reseller.panel.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import com.reseller.panel.entity.enums.Role;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final String SECRET = "myverystrongsecretkeymyverystrongsecret"; // min 32 chars
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Generate JWT Token
    public String generateToken(String username, Role role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(key)
                .compact();
    }

    // Extract username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract role
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Validate token with username
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}