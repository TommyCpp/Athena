package com.athena.repository.jpa;

import com.athena.exception.internal.NotCopyException;
import com.athena.model.borrow.Borrow;
import com.athena.model.publication.Publication;

/**
 * Created by Tommy on 2017/11/5.
 */
public interface BorrowRepositoryCustom {
    Publication getPublication(Borrow borrow) throws ClassNotFoundException, NotCopyException;
}
