package com.athena.service;

import com.athena.model.Book;
import com.athena.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 2017/3/28.
 */
@Service
public class BookService {

    private final BookRepository repository;


    /**
     * Instantiates a new Book service.
     *
     * @param repository the repository
     */
    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
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
            result.addAll(this.repository.getBooksByTitleContains(name));
        }
        return this.ListToPage(pageable, result);
    }


    /**
     * @param pageable the pageable
     * @param name     search terms
     * @return page instance contains all book fit the search term
     */
    public Page<Book> searchBookByFullName(Pageable pageable, String name) {
        return this.ListToPage(pageable, this.repository.getBooksByTitle(name));
    }

    /**
     * @param pageable the pageable
     * @param pinyins pinyin array
     * @return page instance contains all books fit the search terms
     */
    public Page<Book> searchBookByPinyin(Pageable pageable, String[] pinyins) {
        List<Book> result = new ArrayList<>();
        for (String pinyin : pinyins) {
            result.addAll(this.repository.getBooksByTitlePinyin(pinyin));
        }
        return this.ListToPage(pageable, result);

    }

    private Page<Book> ListToPage(Pageable pageable, List<Book> list) {
        int start = pageable.getOffset();//Get the start index
        int pageSize = pageable.getPageSize();
        int end = (start + pageSize) > list.size() ? list.size() : (start + pageSize);
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
