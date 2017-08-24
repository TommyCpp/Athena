package com.athena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2017/8/24.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class IdOfResourceNotFoundException extends ResourceNotFoundException {
    public IdOfResourceNotFoundException() {
        super();
        this.message = "Resource not found because the id of resource is not exist in database";
        this.code = 4041;
    }
}
