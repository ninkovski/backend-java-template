package com.backend.java.microservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private final String expectedApiKey;
    private final ObjectMapper objectMapper;

    @Autowired
    public ApiKeyFilter(@Value("${app.security.api-key}") String expectedApiKey, ObjectMapper objectMapper) {
        this.expectedApiKey = expectedApiKey;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String contextPath = request.getContextPath() != null ? request.getContextPath() : "";
        String path = request.getRequestURI().substring(contextPath.length());

        // Aplicar solo a endpoints bajo /api/**
        if (path.startsWith("/api/") || path.equals("/api")) {
            String apiKey = request.getHeader("X-API-KEY");
            if (apiKey == null || apiKey.isBlank()) {
                sendUnauthorized(response, request.getRequestURI(), "Missing API key");
                return;
            }
            if (!apiKey.equals(expectedApiKey)) {
                sendUnauthorized(response, request.getRequestURI(), "Invalid API key");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response, String path, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", message);
        body.put("path", path);

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
