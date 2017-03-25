package com.athena.security.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Tommy on 2017/3/26.
 */
public class JwtAuthentication implements Authentication {

    private Account account;
    private boolean isAuthentication;


    public JwtAuthentication(){
        this.isAuthentication = false;
    }

    public JwtAuthentication(Account account) {
        this();
        this.account = account;
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
        return null;
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
