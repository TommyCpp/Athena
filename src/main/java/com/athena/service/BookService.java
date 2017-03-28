package com.athena.service;

import com.athena.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tommy on 2017/3/28.
 *
 */
@Service
public class BookService {

    private final BookRepository repository;


    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }
}
