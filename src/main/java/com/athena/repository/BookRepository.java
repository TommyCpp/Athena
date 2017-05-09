package com.athena.repository;

import com.athena.model.Book;
import com.athena.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tommy on 2017/3/28.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> getBooksByPublisher(Publisher publisher);

    List<Book> getBooksByTitleContains(String title);

}
