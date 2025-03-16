package com.example.jwt.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestLogginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@SuppressWarnings("null") HttpServletRequest request,
            @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") Object handlObject) {
        System.out.println("Request: " + request.getMethod() + " " + request.getRequestURI());
        return true;
    }
}
