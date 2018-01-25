package com.athena.model.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Tommy on 2017/3/26.
 */
public class JwtAuthenticationToken implements Authentication {

    private Account account;
    private boolean isAuthentication;


    public JwtAuthenticationToken(){
        this.isAuthentication = false;
    }

    public JwtAuthenticationToken(Account account) {
        this();
        this.account = account;
    }

    public JwtAuthenticationToken(User user){
        this();
        this.account = new Account(user);
    }

    public JwtAuthenticationToken(Account account,boolean isAuthentication){
        this.account = account;
        this.isAuthentication = isAuthentication;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return account.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.account;
    }

    @Override
    public Object getPrincipal() {
        return getDetails();
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthentication;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.isAuthentication = b;
    }

    @Override
    public String getName() {
        return this.account.getUsername();
    }
}
