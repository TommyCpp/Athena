package com.athena.controller;

import com.athena.annotation.PublicationSearchParam;
import com.athena.exception.http.*;
import com.athena.exception.internal.BatchStoreException;
import com.athena.model.common.Batch;
import com.athena.model.copy.BookCopy;
import com.athena.model.copy.CopyVo;
import com.athena.model.publication.Book;
import com.athena.model.publication.search.BookSearchVo;
import com.athena.service.copy.BookCopyService;
import com.athena.service.publication.BookService;
import com.athena.service.util.BatchService;
import com.athena.service.util.PageableHeaderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
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
            @ApiParam(name = "bookSearchVo", value = "book search vo which contains the info regarding the search")
            @PublicationSearchParam BookSearchVo searchVo,
            HttpServletResponse response,
            HttpServletRequest request
    ) throws MissingServletRequestPartException {
        Page<Book> result = this.bookService.search(searchVo.getSpecification(), searchVo.getPageable());
        if (result != null) {
            this.pageableHeaderService.setHeader(result, request, response);
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
    public ResponseEntity<?> createCopy(@PathVariable Long isbn, @RequestBody List<CopyVo> copyVoList) throws BookNotFoundException, BatchStoreException, URISyntaxException {
        List<BookCopy> bookCopyList = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        Book book = this.bookService.get(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }

        for (CopyVo copyVo : copyVoList) {
            bookCopyList.add(new BookCopy(copyVo, book));
        }
        try {
            this.bookCopyService.add(bookCopyList);
        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(e.getMessage());
        }

        //Create Batch
        for (BookCopy bookCopy : bookCopyList) {
            urls.add(this.baseUrl + "/" + bookCopy.getBook().getIsbn().toString() + "/copy/" + bookCopy.getId().toString());
        }

        Batch batch = new Batch(UUID.randomUUID().toString(), "AbstractCopy", Calendar.getInstance().getTime(), urls);
        try {
            this.batchService.save(batch);
        } catch (DataAccessException mongoDataAccessException) {
            throw new BatchStoreException(bookCopyList, "AbstractCopy");
        }

        return ResponseEntity.created(new URI(this.baseUrl + "/batch/" + batch.getId())).build();
    }

    @GetMapping(path = "/{isbn}/copy")
    public ResponseEntity<?> getCopies(@PathVariable Long isbn) throws ResourceNotFoundByIdException {
        return ResponseEntity.ok(this.bookCopyService.getCopies(isbn));
    }

    @GetMapping(path = "/{isbn}/copy/{id}")
    public ResponseEntity<?> getCopy(@PathVariable Long isbn, @PathVariable Long id) throws InvalidCopyTypeException, ResourceNotFoundByIdException {
        BookCopy copy = this.bookCopyService.get(id);
        return ResponseEntity.ok(copy);
    }

    @DeleteMapping(path = "/{isbn}/copy")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> deleteCopies(@PathVariable Long isbn) throws ResourceNotFoundByIdException {
        this.bookCopyService.deleteCopies(isbn);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{isbn}/copy/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> deleteCopy(@PathVariable Long isbn, @PathVariable Long id, @RequestBody BookCopy copy) throws BookNotFoundException, IsbnAndCopyIdMismatchException {
        this.bookCopyService.deleteCopy(isbn, id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "get book info", response = Book.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "book info"),
            @ApiResponse(code = 401, message = "missing isbn"),
            @ApiResponse(code = 404, message = "not found")
    })
    @RequestMapping(path = "/{isbn:[\\d]+[^\\D]}/", method = RequestMethod.GET, produces = "application/json")
    /*
    Note that we add ^\D to avoid the suffix matching, which is default open in Spring MVC.
    @See https://stackoverflow.com/questions/9020444/spring-uri-template-patterns-with-regular-expressions
    */
    public Book getBooks(@PathVariable Long isbn) throws BookNotFoundException, MissingServletRequestPartException {
        if (isbn == null) {
            throw new MissingServletRequestPartException("isbn");
        }
        Book book = this.bookService.get(isbn);
        if (book != null) {
            //if find
            return book;
        } else {
            //cannot find the book
            throw new BookNotFoundException(isbn);
        }

    }

    @PutMapping(path = "/copy")
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> updateCopy(@RequestBody List<BookCopy> bookCopies) throws IllegalEntityAttributeException, MixedCopyTypeException {
        this.bookCopyService.update(bookCopies);
        return ResponseEntity.noContent().build();
    }
}
