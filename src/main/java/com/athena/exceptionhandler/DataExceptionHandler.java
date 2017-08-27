package com.athena.exceptionhandler;

import com.athena.exception.BatchStoreException;
import com.athena.model.Book;
import com.athena.model.Copy;
import com.athena.service.BookService;
import com.athena.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Created by Tommy on 2017/8/27.
 */
@ControllerAdvice(basePackages = "com.athena.controller")
public class DataExceptionHandler {
    private final CopyService copyService;
    private final BookService bookService;

    @Autowired
    public DataExceptionHandler(CopyService copyService, BookService bookService) {
        this.copyService = copyService;
        this.bookService = bookService;
    }

    @ExceptionHandler
    public ResponseEntity handleBatchStoreException(BatchStoreException exception) {
        switch (exception.type) {
            case "Book": {
                //if happen in book controller
                if (exception.elements.size() != 0 && exception.elements.get(0) instanceof Book) {
                    bookService.removeBooks((List<Book>) exception.elements);
                }
            }
            case "Copy":{
                if(exception.elements.size() != 0 && exception.elements.get(0) instanceof Copy){
                    copyService.removeCopies((List<Copy>) exception.elements);
                }
            }
        }
        return ResponseEntity.status(500).body(exception);
    }
}
