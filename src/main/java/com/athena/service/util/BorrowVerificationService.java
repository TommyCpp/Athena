package com.athena.service.util;

import com.athena.model.Borrow;
import com.athena.model.CopyStatus;
import com.athena.model.SimpleCopy;
import com.athena.repository.jpa.BorrowRepository;
import com.athena.repository.jpa.copy.SimpleCopyRepository;
import com.athena.security.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

//import javax.transaction.Transactional;

/**
 * Created by Tommy on 2017/11/10.
 * Check if the Account has enough authentication to borrow books
 */
@Service
public class BorrowVerificationService {

    private final SimpleCopyRepository simpleCopyRepository;
    private BorrowRepository borrowRepository;
    private Integer publicationLimit;

    @Autowired
    public BorrowVerificationService(BorrowRepository borrowRepository, SimpleCopyRepository simpleCopyRepository, @Value("${borrow.publication.limit}") Integer publicationLimit) {
        this.simpleCopyRepository = simpleCopyRepository;
        this.borrowRepository = borrowRepository;
        this.publicationLimit = publicationLimit;
    }

    @Transactional(readOnly = true)
    public boolean userCanBorrow(Account account) {
        //todo: add more rule
        //example: distinguish how many book can an account borrow and how many audio?
        //or how many chinese can borrow and how many foreign can borrow.
        //maybe can use mongoDB to control the configuration of borrow book limit
        List<Borrow> hasBorrowed = this.borrowRepository.findAllByUserAndEnableIsTrue(account.getUser());
        return hasBorrowed.size() <= this.publicationLimit;
    }

    @Transactional(readOnly = true)
    public boolean copyCanBorrow(SimpleCopy simpleCopy) {
        simpleCopy = this.simpleCopyRepository.findOne(simpleCopy.getId());
        return simpleCopy.getStatus() == CopyStatus.AVAILABLE;
    }


    @Transactional(readOnly = true)
    public boolean canReturn(Borrow borrow) {
        Objects.requireNonNull(borrow);
        if (borrow.getEnable()) {
            return false;
        }
        Optional<Borrow> queriedBorrow = this.borrowRepository.findFirstByIdAndEnable(borrow.getId(), true);
        return queriedBorrow.isPresent();
    }


}
