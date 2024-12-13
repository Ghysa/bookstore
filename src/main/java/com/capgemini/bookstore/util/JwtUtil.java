package com.capgemini.bookstore.util;

import com.capgemini.bookstore.error.exception.JwtAuthenticationException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;

public final class JwtUtil {
    private static final SecretKey SECRET_KEY = generateSecretKey();
    public static final long EXPIRATION_TIME = 600_000; // 10 min

    private JwtUtil() {}

    public static String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        return Jwts.builder()
                .subject(username)
                .claim("authorities", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String verifyAndExtractUsername(String authHeader) {
        String token = authHeader.substring(7);
        try {
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            throw new JwtAuthenticationException("Verification of token failed", e);
        }
    }

    private static SecretKey generateSecretKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode("MYSECRETGJEIZJGKZJGKLZJGKLMYSECRETGJEIZJGKZJGKLZJGKLMYSECRETGJEIZJGKZJGKLZJGKLMYSECRETGJEIZJGKZJGKLZJGKLMYSECRETGJEIZJGKZJGKLZJGKLMYSECRETGJEIZJGKZJGKLZJGKLG");
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (WeakKeyException e) {
            throw new RuntimeException("Couldn't create secret key", e);
        }
    }

}