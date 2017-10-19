package com.athena.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Tommy on 2017/9/9.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class IsbnAndCopyIdMismatchException extends ResourceNotFoundException {
    public Long isbn;
    public Long id;

    public IsbnAndCopyIdMismatchException(Long isbn, Long id) {
        this.isbn = isbn;
        this.id = id;
        this.code = 4002;
        this.message = String.format("the book with isbn %s has copy with id %s", isbn, id);
    }
}
