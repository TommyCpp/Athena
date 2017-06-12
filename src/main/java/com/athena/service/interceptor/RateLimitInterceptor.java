package com.athena.service.interceptor;

import com.athena.service.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RateLimitService rateLimitService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            // If has login in
            return true;
        } else {
            return true;
//            TODO: filter the request by the request times
        }
    }
}
