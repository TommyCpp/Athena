package com.athena.exception.http;

import com.athena.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by zhong on 2018/10/4.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class PermissionRequiredException extends BaseException {
    public PermissionRequiredException(String msg) {
        this.message = msg;
        this.code = 4032;
    }
}
