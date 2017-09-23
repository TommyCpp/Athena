package com.athena.repository.jpa;

import com.athena.model.BookCopy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Tommy on 2017/9/24.
 */
public class BookCopyRepositoryImpl implements CopyRepositoryCustom<BookCopy> {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void update(BookCopy copy) {
        Query query = em.createNativeQuery("UPDATE book_copy INNER JOIN copy ON copy.id = book_copy.copy_id SET copy_id=?1,isbn=?2,status=?3,updated_date=?4,created_date=?5 WHERE copy_id=?1");
        query.setParameter(1, copy.getId());
        query.setParameter(2, copy.getBook().getIsbn());
        query.setParameter(3, copy.getStatus());
        query.setParameter(4, copy.getUpdatedDate());
        query.setParameter(5, copy.getCreatedDate());
        query.executeUpdate();
    }
}
