package com.capgemini.bookstore.config.security;

import com.capgemini.bookstore.error.exception.JwtAuthenticationException;
import com.capgemini.bookstore.service.LoginService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final LoginService loginService;

    public JwtAuthenticationFilter(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        // Only allow login without token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            if (request.getServletPath().contains("/login")
                    || request.getServletPath().contains("/users")
                    || request.getServletPath().contains("/h2-console")
                    || request.getServletPath().contains("/api-docs")
                    || request.getServletPath().contains("/swagger-ui")) {
                filterChain.doFilter(request, response);
                return;
            } else {
                throw new JwtAuthenticationException("No authentication present!");
            }
        }

        // Login
        loginService.verifyJwtAndLogin(authHeader);

        filterChain.doFilter(request, response);
    }
}
