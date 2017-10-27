package com.athena.exceptionhandler;

import com.athena.exception.http.ResourceNotDeletable;
import com.athena.exception.internal.BatchStoreException;
import com.athena.model.Book;
import com.athena.model.Copy;
import com.athena.service.BookService;
import com.athena.service.copy.SimpleCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tommy on 2017/8/27.
 */
@ControllerAdvice(basePackages = "com.athena.controller")
public class DataExceptionHandler {
    private final SimpleCopyService simpleCopyService;
    private final BookService bookService;

    @Autowired
    public DataExceptionHandler(SimpleCopyService simpleCopyService, BookService bookService) {
        this.simpleCopyService = simpleCopyService;
        this.bookService = bookService;
    }

    @ExceptionHandler
    public ResponseEntity handleBatchStoreException(BatchStoreException exception) throws ResourceNotDeletable {
        switch (exception.type) {
            case "Book": {
                //if happen in book controller
                if (exception.elements.size() != 0 && exception.elements.get(0) instanceof Book) {
                    bookService.delete((List<Book>) exception.elements);
                }
            }
            break;
            case "Copy": {
                if (exception.elements.size() != 0 && exception.elements.get(0) instanceof Copy) {
                    List<Long> idList = exception.elements.stream().map(o -> ((Copy) o).getId()).collect(Collectors.toList());
                    simpleCopyService.deleteById(idList);
                }
            }
            break;
            default:
        }
        return ResponseEntity.status(500).body(exception);
    }
}
