package com.athena.repository.jpa;

import com.athena.exception.internal.NotCopyException;
import com.athena.model.Borrow;
import com.athena.model.Publication;
import com.athena.model.PublicationCopy;
import com.athena.model.SimpleCopy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;

/**
 * Created by Tommy on 2017/11/5.
 */
public class BorrowRepositoryImpl implements BorrowRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public Publication getPublication(Borrow borrow) throws ClassNotFoundException, NotCopyException {
        Objects.requireNonNull(borrow);

        Class targetClass = Class.forName(borrow.getType());

        if (!targetClass.isInstance(SimpleCopy.class)) {
            //if the targetClass is not the child of SimpleCopy
            throw new NotCopyException(targetClass);
        }

        Object result = this.entityManager.find(targetClass, borrow.getCopy().getId());
        if (!(result instanceof PublicationCopy)) {
            throw new NotCopyException(targetClass);
        }
        return ((PublicationCopy) result).getPublication();
    }




}
