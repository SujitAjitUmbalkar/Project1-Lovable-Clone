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

        final String requestsHeaderToken = request.getHeader("Authorization");

        if (requestsHeaderToken == null || !requestsHeaderToken.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String token = requestsHeaderToken.replace("Bearer ", "");

        JwtUserPrinciple user = authUtils.verifyAccessToken(token);

        if (user != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UsernamePasswordAuthenticationToken authenticationToken = new  UsernamePasswordAuthenticationToken(
                    user,null, user.authorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

    }
}
