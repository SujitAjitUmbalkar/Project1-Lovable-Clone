package com.codingshuttle.projects.lovable_clone.security;

import com.codingshuttle.projects.lovable_clone.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

@Component
public class AuthUtils
{

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    private SecretKey getSecretKey()
    {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user)
    {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(getSecretKey())
                .compact();

    }

    public JwtUserPrinciple verifyAccessToken(String token)
    {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Long userId =Long.parseLong(claims.get("userId").toString());
        String username = claims.getSubject();

        return new JwtUserPrinciple(userId, username,new ArrayList<>());
    }

    public Long getCurrentUserId()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Pattern variable: checks type and automatically casts to 'userPrinciple' in one step
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrinciple userPrinciple))
        {
            throw new AuthenticationCredentialsNotFoundException("JWT Not Found");
        }

//        JwtUserPrinciple jwtUserPrinciple = (JwtUserPrinciple) authentication.getPrincipal(); get rid of this by doing pattern variable

        return userPrinciple.userId();
    }

}
