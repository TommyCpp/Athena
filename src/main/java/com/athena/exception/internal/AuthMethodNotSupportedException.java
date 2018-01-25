package com.athena.exception.internal;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * Created by tommy on 2017/3/21.
 */
public class AuthMethodNotSupportedException extends AuthenticationServiceException{
    public AuthMethodNotSupportedException(String s) {
        super(s);
    }
}
