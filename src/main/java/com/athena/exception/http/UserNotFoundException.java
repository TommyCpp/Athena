package com.athena.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2018/7/3.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ResourceNotFoundByIdException {
    public Long id;

    public UserNotFoundException(Long id) {
        super();
        this.message = "cannot find the requested user";
        this.code = 4041;
        this.id = id;
    }
}
