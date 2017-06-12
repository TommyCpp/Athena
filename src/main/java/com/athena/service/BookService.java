package com.athena.service;

import com.athena.model.Book;
import com.athena.model.Publisher;
import com.athena.repository.BookRepository;
import com.athena.repository.PublisherRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by tommy on 2017/3/28.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;


    /**
     * Instantiates a new Book service.
     *
     * @param bookRepository the bookRepository
     */
    @Autowired
    public BookService(BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }


    /**
     * Search book by name.
     *
     * @param pageable the pageable
     * @param names    the names
     * @return the page
     */
    public Page<Book> searchBookByName(Pageable pageable, String[] names) {
        List<Book> result = new ArrayList<>();
        for (String name :
                names) {
            result.addAll(this.bookRepository.getBooksByTitleContains(name));
        }
        return this.ListToPage(pageable, result);
    }


    /**
     * @param pageable the pageable
     * @param name     search terms
     * @return page instance contains all book fit the search term
     */
    public Page<Book> searchBookByFullName(Pageable pageable, String name) {
        return this.bookRepository.getBooksByTitle(pageable, name);
    }

    /**
     * @param pageable the pageable
     * @param pinyins  pinyin array
     * @return page instance contains all books fit the search terms
     */
    public Page<Book> searchBookByPinyin(Pageable pageable, String[] pinyins) {
        List<Book> result = new ArrayList<>();
        for (String pinyin : pinyins) {
            result.addAll(this.bookRepository.getBooksByTitlePinyin(pinyin));
        }
        return this.ListToPage(pageable, result);

    }

    public Page<Book> searchBookByAuthor(Pageable pageable, String author) {
        return bookRepository.getBookBy_authorContains(pageable, author);
    }

    private Page<Book> ListToPage(Pageable pageable, List<Book> list) {
        int start = pageable.getOffset();//Get the start index
        int pageSize = pageable.getPageSize();
        int end = (start + pageSize) > list.size() ? list.size() : (start + pageSize);
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    /**
     * Search for all books contains the requested author
     *
     * @param pageable pageable
     * @param authors  author array
     * @return page
     */
    public Page<Book> searchBookByAuthors(Pageable pageable, String[] authors) {
        Set<Book> result = new HashSet<>();
        for (int i = 0; i < authors.length; i++) {
            List<Book> books = bookRepository.getBookBy_authorContains(authors[i]);
            for (Book book : books) {
                boolean flag = true;
                for (int j = 0; j < authors.length; j++) {
                    if (j != i) {
                        List<String> bookAuthors = book.getAuthor();
                        if (!bookAuthors.contains(authors[j])) {
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    result.add(book);
                }
            }
        }
        return ListToPage(pageable, new ArrayList<>(result));
    }

    public Page<Book> searchBookByFullAuthors(Pageable pageable, String[] authors) {
        Arrays.sort(authors);
        return bookRepository.getBookBy_author(pageable, StringUtils.join(authors, ","));
    }

    public Page<Book> searchBookByPublisher(Pageable pageable, String publisherName) {
        return bookRepository.getBookByPublisher(pageable, publisherRepository.findPublisherByName(publisherName));
    }
}
