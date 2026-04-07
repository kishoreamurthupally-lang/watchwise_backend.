package com.Movies.watchwise_backend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil util;

    public JwtFilter(JwtUtil util) {
        this.util = util;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = req.getRequestURI();

        //  Skip auth for public endpoints
        if (path.contains("/api/auth")) {
            chain.doFilter(req, res);
            return;
        }

        String authHeader = req.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String email = util.extractEmail(token);
                String role = util.extractRole(token);

                //  Normalize role — always ROLE ADMIN or ROLE USER
                String cleanRole = (role != null ? role : "USER")
                        .replace("ROLE_", "")
                        .toUpperCase();
                String authority = "ROLE_" + cleanRole;

                System.out.println(" JWT -> Email: " + email + " | Authority: " + authority);

                var authorities = List.of(new SimpleGrantedAuthority(authority));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                System.out.println(" JWT Error: " + e.getMessage());
            }
        }

        chain.doFilter(req, res);
    }
}