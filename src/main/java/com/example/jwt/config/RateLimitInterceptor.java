package com.example.jwt.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {
    private final Bucket bucket;

    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            // Success: Add remaining tokens header and allow the request to proceed
            response.addHeader("X-RateLimit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        }

        // Rate limit exceeded: Set response details
        response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE); // 503
        response.addHeader("X-Rate-Limit-Retry-After-Milliseconds",
                String.valueOf(probe.getNanosToWaitForRefill() / 1_000_000));

        // Set content type and write a custom message to the response body
        response.setContentType("application/json");
        String errorMessage = "{\"error\": \"Rate limit exceeded. Please try again later.\"}";
        try {
            response.getWriter().write(errorMessage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write rate limit error message", e);
        }

        return false;
    }
}