package com.athena.repository.jpa;

import com.athena.model.Book;
import com.athena.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    Page<Book> getBooksByTitle(Pageable pageable, String name);

    @Query(nativeQuery = true, value = "SELECT * FROM book WHERE REPLACE(title_pinyin,',','') = ?1")
    List<Book> getBooksByTitlePinyin(String pinyin);

    Page<Book> getBookByAuthorContains(Pageable pageable, String author);

    List<Book> getBookByAuthorContains(String author);

    default Page<Book> getBookByAuthor(Pageable pageable, List<String> authors) {
        StringBuilder authorQueryBuilder = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            if (i != authors.size() - 1) {
                authorQueryBuilder.append(authors.get(i).replace("'", "\'")).append(",");
            }
            authorQueryBuilder.append(authors.get(i).replace("'", "\'"));
        }
        String authorQuery = authorQueryBuilder.toString();

        Integer start = pageable.getOffset() - 1;
        Integer end = pageable.getOffset() + pageable.getPageSize();


        long total = this.countGetBookByAuthorExactMatch(authorQuery, authors.size());
        List<Book> queryResult = this.getBookByAuthorExactMatch(authorQuery, authors.size(), start, end);
        return new PageImpl<>(queryResult, pageable.next(), total);//todo:test
    }

    @Query(nativeQuery = true, value = "SELECT COUNT(*)" +
            "FROM book " +
            "  INNER JOIN book_author ON book.isbn = book_author.isbn " +
            "  INNER JOIN (SELECT book.isbn " +
            "        FROM book " +
            "          LEFT JOIN book_author ON book.isbn = book_author.isbn " +
            "        WHERE book_author.author_name IN (?1) " +
            "        GROUP BY book.isbn " +
            "        HAVING COUNT(*) = ?2) AS ba2 ON ba2.isbn = book_author.isbn " +
            "GROUP BY book.isbn " +
            "HAVING COUNT(*) = ?2")
    Integer countGetBookByAuthorExactMatch(String authorQuery, Integer authorCount);

    @Query(nativeQuery = true, value = "SELECT book.* " +
            "FROM book " +
            "  INNER JOIN book_author ON book.isbn = book_author.isbn " +
            "  INNER JOIN (SELECT book.isbn " +
            "        FROM book " +
            "          LEFT JOIN book_author ON book.isbn = book_author.isbn " +
            "        WHERE book_author.author_name IN (?1) " +
            "        GROUP BY book.isbn " +
            "        HAVING COUNT(*) = ?2) AS ba2 ON ba2.isbn = book_author.isbn " +
            "GROUP BY book.isbn " +
            "HAVING COUNT(*) = ?2 " +
            "BETWEEN ?3 AND ?4 ")
    List<Book> getBookByAuthorExactMatch(String authorQuery, Integer authorCount, Integer start, Integer end);

    Page<Book> getBookByPublisher(Pageable pageable, Publisher publisher);


}
