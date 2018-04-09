package com.athena.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2017/8/26.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends ResourceNotFoundByIdException {
    public Long isbn;

    public BookNotFoundException(Long isbn) {
        super();
        this.message = "cannot find the requested book, please check the isbn";
        this.code = 40411;
        this.isbn = isbn;
    }
}
