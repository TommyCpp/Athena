package com.athena.security.service;

import com.athena.security.model.Account;
import com.athena.security.model.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by Tommy on 2017/3/26.
 *
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JwtAuthenticationProvider(AccountService accountService, PasswordEncoder passEncoder){
        this.accountService = accountService;
        this.passwordEncoder = passEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Account intendAccount = (Account) authentication.getPrincipal();
        Account actualAccount = accountService.loadAccountById(intendAccount.getId());
        if (passwordEncoder.matches(intendAccount.getPassword(),actualAccount.getPassword())) {
            //If the account matches
            return new JwtAuthenticationToken(actualAccount, true);
        }
        else{
            throw new BadCredentialsException("Password doesn't match");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
