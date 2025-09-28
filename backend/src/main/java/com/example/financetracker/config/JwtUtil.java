package com.example.financetracker.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final byte[] secretBytes;

    @Value("${jwt.expiration:86400000}")
    private long expirationMs;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretBytes = secret.getBytes(StandardCharsets.UTF_8);
    }

    // --- new API ---
    public String generateToken(String subject) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretBytes)
                .compact();
    }

    public String extractUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        String username = extractUsername(token);
        return username != null && username.equals(user.getUsername()) && !isExpired(token);
    }

    // --- legacy compatibility for existing controllers ---
    /** Kept for older controllers that call jwtUtil.validateToken(token). */
    public boolean validateToken(String token) {
        try {
            // just ensure it parses and is not expired
            return !isExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /** Kept for older controllers that call jwtUtil.extractEmail(token). */
    public String extractEmail(String token) {
        return extractUsername(token);
    }

    // --- internals ---
    private boolean isExpired(String token) {
        Date exp = getAllClaims(token).getExpiration();
        return exp.before(new Date());
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretBytes)
                .parseClaimsJws(token)
                .getBody();
    }
}
