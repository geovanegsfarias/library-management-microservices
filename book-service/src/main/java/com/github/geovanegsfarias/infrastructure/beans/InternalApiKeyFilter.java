package com.github.geovanegsfarias.infrastructure.beans;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class InternalApiKeyFilter extends OncePerRequestFilter {

    private final ApiKeyConfigurationProperties apiKeyConfigurationProperties;

    public InternalApiKeyFilter(ApiKeyConfigurationProperties apiKeyConfigurationProperties) {
        this.apiKeyConfigurationProperties = apiKeyConfigurationProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var path = request.getRequestURI();

        if (path.endsWith("/reserve") || path.endsWith("/return")) {
            var requestHeader = request.getHeader("X-Api-Key");

            if (!apiKeyConfigurationProperties.apiKey().equals(requestHeader)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized access");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
