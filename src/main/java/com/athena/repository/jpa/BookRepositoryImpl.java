package com.athena.repository.jpa;

import com.athena.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Tommy on 2018/1/3.
 */
public class BookRepositoryImpl implements CustomBookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Book> getBookByAuthor(Pageable pageable, List<String> authors) {
        StringBuilder authorQueryBuilder = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            String author = authors.get(i).replace("'", "\'");
            author = "'" + author + "'";
            if (i != authors.size() - 1) {
                authorQueryBuilder.append(author).append(",");
            } else {
                authorQueryBuilder.append(author);
            }
        }
        String authorQuery = authorQueryBuilder.toString();

        Integer start = pageable.getOffset();
        Integer pageSize = pageable.getPageSize();

        Query countQuery = this.entityManager.createNativeQuery("SELECT COUNT(DISTINCT book.isbn) " +
                "FROM book " +
                "  INNER JOIN book_author ON book.isbn = book_author.isbn " +
                "  INNER JOIN (SELECT book.isbn " +
                "        FROM book " +
                "          LEFT JOIN book_author ON book.isbn = book_author.isbn " +
                "        WHERE book_author.author_name IN (" + authorQuery + ")" +
                "        GROUP BY book.isbn " +
                "        HAVING COUNT(*) = ?1) AS ba2 ON ba2.isbn = book_author.isbn " +
                "GROUP BY book.isbn " +
                "HAVING COUNT(*) = ?1");

        countQuery.setParameter(1, authors.size());
        List countQueryResult = countQuery.getResultList();
        long total = 0;
        if (countQueryResult.size() > 0) {
            total = Long.valueOf(countQueryResult.get(0).toString());
        }


        Query query = this.entityManager.createNativeQuery("SELECT book.* " +
                " FROM book " +
                "  INNER JOIN book_author ON book.isbn = book_author.isbn " +
                "  INNER JOIN (SELECT book.isbn " +
                "        FROM book " +
                "          LEFT JOIN book_author ON book.isbn = book_author.isbn " +
                "        WHERE book_author.author_name IN (" + authorQuery + ") " +
                "        GROUP BY book.isbn " +
                "        HAVING COUNT(*) = ?1) AS ba2 ON ba2.isbn = book_author.isbn " +
                "GROUP BY book.isbn " +
                "HAVING COUNT(*) = ?1 " +
                "LIMIT ?2, ?3 ", Book.class);
        query.setParameter(1, authors.size());
        query.setParameter(2, start);
        query.setParameter(3, pageSize);

        List queryResult = query.getResultList();
        return new PageImpl<Book>(queryResult, pageable.next(), total);//todo:test
    }
}
