package com.athena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2017/9/2.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidCopyTypeException extends BaseException {
    public InvalidCopyTypeException() {
        this.code = 4001;
        this.statusCode = 400;
        this.message = "The type of copy is invalid, mostly because the type is not exist";
    }
}
