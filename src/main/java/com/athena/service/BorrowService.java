package com.athena.service;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.exception.http.ResourceNotDeletable;
import com.athena.model.Borrow;

/**
 * Created by Tommy on 2017/11/5.
 */
public class BorrowService implements ModelCRUDService<Borrow, String> {
    @Override
    public Borrow add(Borrow borrow) {
        return null;
    }

    @Override
    public Borrow get(String s) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        return null;
    }

    @Override
    public Borrow update(Borrow borrow) throws IdOfResourceNotFoundException, IllegalEntityAttributeException {
        return null;
    }

    @Override
    public void delete(Borrow borrow) throws IdOfResourceNotFoundException, ResourceNotDeletable {

    }
}
