package com.athena.repository.jpa;

import com.athena.model.publication.Book;
import com.athena.model.publication.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tommy on 2017/3/28.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, CustomBookRepository, JpaSpecificationExecutor {
    List<Book> getBooksByPublisher(Publisher publisher);

    List<Book> getBooksByTitleContains(String title);

    Page<Book> getBooksByTitle(Pageable pageable, String name);

    @Query(nativeQuery = true, value = "SELECT * FROM book WHERE REPLACE(title_pinyin,',','') = ?1")
    List<Book> getBooksByTitlePinyin(String pinyin);

    Page<Book> getBookByAuthor(Pageable pageable, String author);

    List<Book> getBookByAuthor(String author);

    Page<Book> getBookByPublisher(Pageable pageable, Publisher publisher);

}
