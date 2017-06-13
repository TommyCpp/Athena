package com.athena.service.interceptor;

import com.athena.exception.AntiSpiderException;
import com.athena.service.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private int limit;

    @Autowired
    public RateLimitInterceptor(@Value("${search.limit.get.times}") int limit, RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
        this.limit = limit;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            // If has login in
            return true;
        } else {
            String ipAddress = request.getRemoteAddr();
            if (rateLimitService.increaseLimit(ipAddress) > limit) {
                throw new AntiSpiderException(429, 4291, "Too many search request");
//                TODO: 1. throw exception has performance issue, need to decide whether throw or return directly; 2. handle exception
            }

            return false;
        }
    }
}
