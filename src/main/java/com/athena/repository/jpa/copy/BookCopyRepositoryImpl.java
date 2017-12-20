package com.athena.repository.jpa.copy;

import com.athena.model.BookCopy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2017/9/24.
 */
@Component
public class BookCopyRepositoryImpl implements CopyRepositoryCustom<BookCopy, Long> {

    @PersistenceContext
    private EntityManager em;

    @Value("${copy.status.isDeletable}")
    private String deletable;


    @Override
    public BookCopy update(BookCopy copy) {
        Query query = em.createNativeQuery("UPDATE book_copy INNER JOIN copy ON copy.id = book_copy.copy_id SET copy_id=?1,isbn=?2,status=?3,updated_date=?4,created_date=?5 WHERE copy_id=?1");
        query.setParameter(1, copy.getId());
        query.setParameter(2, copy.getBook().getIsbn());
        query.setParameter(3, copy.getStatus());
        query.setParameter(4, copy.getUpdatedDate());
        query.setParameter(5, copy.getCreatedDate());
        query.executeUpdate();
        em.flush();
        return copy;
    }

    @Override
    public List<BookCopy> isNotDeletable(Long id) {
        String[] deletableStrings = this.deletable.split(",");
        List<Integer> deletableInt = new ArrayList<>(deletableStrings.length);
        for (int i = 0; i < deletableStrings.length; i++) {
            deletableInt.add(Integer.valueOf(deletableStrings[i]));
        }
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(BookCopy.class);
        Root target = criteriaQuery.from(BookCopy.class);
        criteriaQuery.where(
                builder.and(
                        builder.not(target.get("status").in(deletableStrings)),
                        builder.equal(target.get("book").get("isbn"), id)
                )
        );
        Query query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }


}
