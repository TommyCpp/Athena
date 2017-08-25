package com.athena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2017/8/26.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends ResourceNotFoundException {
    public Long isbn;

    public BookNotFoundException(Long isbn) {
        super();
        this.message = "Copy's correspond book do not exist. Check the isbn below";
        this.code = 4042;
        this.isbn = isbn;
    }
}
