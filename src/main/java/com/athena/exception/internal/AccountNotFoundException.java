package com.athena.exception.internal;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Tommy on 2017/7/1.
 */
public class AccountNotFoundException extends AuthenticationException {

    public AccountNotFoundException(){
        super("Account Not Found");
    }
    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
