package com.athena.repository;

import com.athena.model.Book;
import com.athena.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tommy on 2017/3/28.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> getBooksByPublisher(Publisher publisher);

    List<Book> getBooksByTitleContains(String title);

    List<Book> getBooksByTitle(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM book WHERE REPLACE(title_pinyin,',','') = ?1")
    List<Book> getBooksByTitlePinyin(String pinyin);

    List<Book> getBookBy_authorContains(String author);
}
