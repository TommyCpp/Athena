package com.athena.security.filter;

import com.athena.security.service.TokenAuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by tommy on 2017/3/22.
 */
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final TokenAuthenticationService tokenAuthenticationService;

    /**
     * Instantiates a new Jwt authentication filter.
     *
     * @param tokenAuthenticationService the token authentication service
     */
    public JwtAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) servletRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(servletRequest, servletResponse);
            SecurityContextHolder.getContext().setAuthentication(null);//Clear after request
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

}
