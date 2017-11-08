package com.athena.service;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.exception.http.ResourceNotDeletable;
import com.athena.model.Borrow;
import com.athena.repository.jpa.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Tommy on 2017/11/5.
 */
public class BorrowService implements ModelCRUDService<Borrow, String> {

    private BorrowRepository borrowRepository;

    @Autowired
    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    @Override
    public Borrow add(Borrow borrow) {
        return this.borrowRepository.save(borrow);
    }

    @Override
    public Borrow get(String id) throws IdOfResourceNotFoundException {
        Borrow borrow = this.borrowRepository.findOne(id);
        if (borrow == null) {
            throw new IdOfResourceNotFoundException();
        }
        return borrow;
    }

    @Override
    public Borrow update(Borrow borrow) throws IdOfResourceNotFoundException {
        if (!this.borrowRepository.exists(borrow.getId())) {
            throw new IdOfResourceNotFoundException();
        }
        this.borrowRepository.save(borrow);
        return borrow;
    }

    @Override
    public void delete(Borrow borrow) throws IdOfResourceNotFoundException, ResourceNotDeletable {

    }
}
