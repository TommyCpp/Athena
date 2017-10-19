package com.athena.controller;

import com.athena.exception.http.*;
import com.athena.exception.internal.BatchStoreException;
import com.athena.model.Batch;
import com.athena.model.Book;
import com.athena.model.BookCopy;
import com.athena.model.CopyInfo;
import com.athena.service.BatchService;
import com.athena.service.BookService;
import com.athena.service.copy.BookCopyService;
import com.athena.service.util.PageableHeaderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@Api(value = "Book", description = "Manage books")
public class BookController {
    private final BookService bookService;

    private final PageableHeaderService pageableHeaderService;
    private final String baseUrl;
    private final String bookUrl;
    private final BatchService batchService;
    private final BookCopyService bookCopyService;

    @Autowired
    public BookController(BookService bookService, PageableHeaderService pageableHeaderService, BatchService batchService, BookCopyService bookCopyService, @Value("${web.url}") String baseUrl) {
        this.bookService = bookService;
        this.pageableHeaderService = pageableHeaderService;
        this.batchService = batchService;
        this.bookCopyService = bookCopyService;
        this.baseUrl = baseUrl;
        this.bookUrl = baseUrl + "/books";
    }

    @ApiOperation(value = "search book", response = Page.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "search success"),
            @ApiResponse(code = 401, message = "search term is missing")
    })
    @RequestMapping(path = "/**", method = RequestMethod.GET, produces = "application/json")
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

    @ApiOperation(value = "create book info", authorizations = {
            @Authorization(
                    value = "admin/superadmin"
            )
    },
            response = Book.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Data access exception"),
    })
    @RequestMapping(path = "/**", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> createBooks(@RequestBody List<Book> books) throws URISyntaxException, BatchStoreException {
        try {
            bookService.add(books);
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

    @ApiOperation(value = "create copy", authorizations = {
            @Authorization(
                    value = "admin/superadmin"
            )
    },
            response = Void.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Copy's correspond book do not exist")
    })
    @PostMapping(path = "/{isbn}/copy/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> createCopy(@PathVariable Long isbn, @RequestBody List<CopyInfo> copyInfoList) throws BookNotFoundException, BatchStoreException, URISyntaxException {
        List<BookCopy> bookCopyList = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        Book book = this.bookService.get(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }

        for (CopyInfo copyInfo : copyInfoList) {
            bookCopyList.add(new BookCopy(copyInfo, book));
        }
        try {
            this.bookCopyService.addCopies(bookCopyList);
        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(e.getMessage());
        }

        //Create Batch
        for (BookCopy bookCopy : bookCopyList) {
            urls.add(this.baseUrl + "/" + bookCopy.getBook().getIsbn().toString() + "/copy/" + bookCopy.getId().toString());
        }

        Batch batch = new Batch(UUID.randomUUID().toString(), "Copy", Calendar.getInstance().getTime(), urls);
        try {
            this.batchService.save(batch);
        } catch (DataAccessException mongoDataAccessException) {
            throw new BatchStoreException(bookCopyList, "Copy");
        }

        return ResponseEntity.created(new URI(this.baseUrl + "/batch/" + batch.getId())).build();
    }

    @GetMapping(path = "/{isbn}/copy/")
    public ResponseEntity<?> getCopies(@PathVariable Long isbn) throws IdOfResourceNotFoundException {
        return ResponseEntity.ok(this.bookCopyService.getCopies(isbn));
    }

    @GetMapping(path = "/{isbn}/copy/{id}")
    public ResponseEntity<?> getCopy(@PathVariable Long isbn, @PathVariable Long id) throws InvalidCopyTypeException, IdOfResourceNotFoundException {
        BookCopy copy = this.bookCopyService.getCopy(id);
        return ResponseEntity.ok(copy);
    }

    @DeleteMapping(path = "/{isbn}/copy")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> deleteCopies(@PathVariable Long isbn) throws IdOfResourceNotFoundException {
        this.bookCopyService.deleteCopies(isbn);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{isbn}/copy/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> deleteCopy(@PathVariable Long isbn, @PathVariable Long id, @RequestBody BookCopy copy) throws BookNotFoundException, IsbnAndCopyIdMismatchException {
        this.bookCopyService.deleteCopy(isbn, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/copy")
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> updateCopy(@RequestBody List<BookCopy> bookCopies) throws IllegalEntityAttributeException, MixedCopyTypeException {
        this.bookCopyService.updateCopies(bookCopies);
        return ResponseEntity.noContent().build();
    }
}
