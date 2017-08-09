package com.athena.security.filter;

import com.athena.security.exception.AuthMethodNotSupportedException;
import com.athena.security.model.Account;
import com.athena.security.model.JwtAuthenticationToken;
import com.athena.security.service.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tommy on 2017/3/22.
 *
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenAuthenticationService tokenAuthenticationService;

    public JwtLoginFilter(String url, AuthenticationManager authenticationManager, TokenAuthenticationService tokenAuthenticationService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if(!httpServletRequest.getMethod().equals("POST")){
            throw new AuthMethodNotSupportedException("Login credentials must be shipped by POST");
        }
        Account account = new Account();
        try {
            account.setId(Long.valueOf(httpServletRequest.getParameter("id")));
            account.setPassword(httpServletRequest.getParameter("password"));
        } catch (NumberFormatException e) {
            throw new BadCredentialsException("Bad Credential");
        }
        return getAuthenticationManager().authenticate(
                new JwtAuthenticationToken(account)
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        assert (authResult instanceof JwtAuthenticationToken);
        tokenAuthenticationService.addAuthentication(response, (Account) authResult.getPrincipal());
    }
}
