package com.codingshuttle.projects.lovable_clone.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter
{
    private final AuthUtils authUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        log.info("Incoming request {}", request.getRequestURI());

        final String requestHeaderToken = request.getHeader("Authorization");

        if (requestHeaderToken == null || !requestHeaderToken.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String token = requestHeaderToken.replace("Bearer ", "");

        try {
            JwtUserPrinciple user = authUtils.verifyAccessToken(token);

            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.authorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
            // Optional: You can choose to clear context or let it pass down unauthenticated
        }

        filterChain.doFilter(request, response);
    }
}
