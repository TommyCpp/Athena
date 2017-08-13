package com.athena.controller;

import com.athena.model.Book;
import com.athena.service.BookService;
import com.athena.service.PageableHeaderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Tommy on 2017/5/14.
 */
@RestController
@RequestMapping("api/v1/**")
public class BookController {
    private final BookService bookService;

    private final PageableHeaderService pageableHeaderService;

    @Autowired
    public BookController(BookService bookService, PageableHeaderService pageableHeaderService) {
        this.bookService = bookService;
        this.pageableHeaderService = pageableHeaderService;
    }

    @RequestMapping(path = "/books", method = RequestMethod.GET)
    public Page<Book> searchBooks(
            @RequestParam(value = "title", required = false) String[] titles,
            @RequestParam(value = "publisher", required = false) String publisher,
            @RequestParam(value = "author", required = false) String[] authors,
            @RequestParam(value = "count", defaultValue = "${search.default.count}") Integer count,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "last_cursor", required = false) Integer lastCursor,
            @RequestParam(value = "match_all", required = false, defaultValue = "false") Boolean matchAll,
            @RequestParam(value = "language", required = false) String language,
            HttpServletResponse response,
            HttpServletRequest request
    ) throws MissingServletRequestPartException {
        Integer startPage = 1;
        if (page != null) {
            startPage = page;
        } else if (lastCursor != null) {
            startPage = lastCursor / count;
        }

        Pageable pageable = new PageRequest(startPage - 1, count); // startPage starts from 1, while the Pageable actually accept the page index start from 0, so need to -1

        Page<Book> result = null;

        if (language == null) {
            //If not specify the language
            if (titles != null) {
                if (!matchAll) {
                    //Search by titles, partial match
                    result = bookService.searchBookByName(pageable, titles);
                    if (result.getTotalElements() == 0) {
                        //If the search gets no results
                        //Consider the input as pinyin and try again
                        result = bookService.searchBookByPinyin(pageable, titles);
                    }
                }
                if (titles.length == 1 && matchAll) {
                    //Search by title, all match
                    result = bookService.searchBookByFullName(pageable, titles[0]);
                }
            }

            if (authors != null) {
                //search by author
                if (matchAll) {

                    result = bookService.searchBookByFullAuthors(pageable, authors);
                } else {
                    if (authors.length == 1) {
                        result = bookService.searchBookByAuthor(pageable, authors[0]);
                    } else {
                        //search books which contains all the author
                        result = bookService.searchBookByAuthors(pageable, authors);
                    }
                }
            }

            if (publisher != null) {
                return bookService.searchBookByPublisher(pageable, publisher);
            }
        }

        if (result != null) {
            pageableHeaderService.setHeader(result, request, response);
            return result;
        }

        throw new MissingServletRequestPartException("search term");

    }

    @RequestMapping(path = "/books", method = RequestMethod.POST)
    public ResponseEntity<?> createBooks(@RequestBody List<Book> books) {
        bookService.saveBooks(books);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
