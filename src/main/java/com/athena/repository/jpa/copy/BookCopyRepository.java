package com.athena.repository.jpa.copy;

import com.athena.model.copy.BookCopy;
import com.athena.model.publication.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Tommy on 2017/8/30.
 */
@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long>, CopyRepositoryCustom<BookCopy, Long> {
    List<BookCopy> findByBook(Book book);

    List<BookCopy> findByIdIsInAndBookIsNotNull(Iterable<Long> idList);

    BookCopy findByIdAndBookIsNotNull(Long id);

    BookCopy findByIdAndBook(Long id, Book book);
}