package com.athena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2017/8/19.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException() {
        this.statusCode = 404;
        this.code = 4040;
        this.message = "Requested resource is not found";
    }

}
