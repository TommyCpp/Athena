package com.athena.service.interceptor;

import com.athena.service.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Tommy on 2017/6/12.
 */
@Service
public class RateLimitInterceptor extends HandlerInterceptorAdapter {
    private final RateLimitService rateLimitService;
    private final String errorResponse;
    private int limit;

    @Autowired
    public RateLimitInterceptor(@Value("${search.limit.get.times}") int limit, RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
        this.limit = limit;
        this.errorResponse = "{\"code\":4291,\"message\":\"Too many search request recently\"}";
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            String ipAddress = request.getRemoteAddr();
            if (rateLimitService.increaseLimit(ipAddress) > limit) {
                response.sendError(429, this.errorResponse);
                return false;
            }
        }
        return true;
    }
}
