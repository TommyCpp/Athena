package com.athena.controller;

import com.athena.exception.BatchStoreException;
import com.athena.model.Batch;
import com.athena.model.Book;
import com.athena.service.BatchService;
import com.athena.service.BookService;
import com.athena.service.PageableHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tommy on 2017/5/14.
 */
@RestController
@RequestMapping("${web.url.prefix}/books/**")
public class BookController {
    private final BookService bookService;

    private final PageableHeaderService pageableHeaderService;
    private final String baseUrl;
    private final String bookUrl;
    private final BatchService batchService;

    @Autowired
    public BookController(BookService bookService, PageableHeaderService pageableHeaderService, BatchService batchService, @Value("${web.url}") String baseUrl) {
        this.bookService = bookService;
        this.pageableHeaderService = pageableHeaderService;
        this.batchService = batchService;
        this.baseUrl = baseUrl;
        this.bookUrl = baseUrl + "/books";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
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

    @RequestMapping(path = "/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> createBooks(@RequestBody List<Book> books) throws URISyntaxException, BatchStoreException {
        try {
            bookService.saveBooks(books);
            List<String> urls = new ArrayList<>();
            for (Book book : books) {
                urls.add(this.bookUrl + "/" + book.getIsbn());
            }
            Batch batch = new Batch(UUID.randomUUID().toString(), "Book", Calendar.getInstance().getTime(), urls);
            try {
                this.batchService.save(batch);
            } catch (DataAccessException mongoDataAccessException) {
                throw new BatchStoreException(books, "Book");
            }
            return ResponseEntity.created(new URI(this.baseUrl + "/batch/" + batch.getId())).build();
        } catch (DataAccessException jpaDataAccessException) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(jpaDataAccessException.getMessage());
        }
    }

}
