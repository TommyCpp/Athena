package com.athena.repository.jpa;

import com.athena.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Tommy on 2018/1/3.
 */
public interface CustomBookRepository {
    Page<Book> getBookByAuthor(Pageable pageable, List<String> authors);
}
