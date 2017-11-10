package com.athena.service.util;

import com.athena.model.Borrow;
import com.athena.repository.jpa.BorrowRepository;
import com.athena.security.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tommy on 2017/11/10.
 * Check if the Account has enough authentication to borrow books
 */
@Service
public class BorrowVerificationService {

    private BorrowRepository borrowRepository;
    private Integer publicationLimit;

    @Autowired
    public BorrowVerificationService(BorrowRepository borrowRepository, @Value("borrow.publication.limit") Integer publicationLimit) {
        this.borrowRepository = borrowRepository;
        this.publicationLimit = publicationLimit;
    }

    public boolean userCanBorrow(Account account) {
        //todo: add more rule
        //example: distinguish how many book can an account borrow and how many audio?
        //or how many chinese can borrow and how many foreign can borrow.
        //maybe can use mongoDB to control the configuration of borrow book limit
        List<Borrow> hasBorrowed = this.borrowRepository.findAllByUserAndEnableIsTrue(account.getUser());
        return hasBorrowed.size() <= this.publicationLimit;
    }


}
