package com.athena.service.borrow;

import com.athena.model.borrow.Borrow;
import com.athena.model.copy.CopyStatus;
import com.athena.model.copy.SimpleCopy;
import com.athena.model.security.User;
import com.athena.repository.jpa.BorrowRepository;
import com.athena.repository.jpa.copy.SimpleCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Created by Tommy on 2017/11/10.
 * Check if the Account has enough authentication to borrow books
 */
@Service
public class BorrowVerificationService {

    private final SimpleCopyRepository simpleCopyRepository;
    private BorrowRepository borrowRepository;
    private Integer publicationLimit;

    /**
     * Instantiates a new Borrow verification service.
     *
     * @param borrowRepository     the borrow repository
     * @param simpleCopyRepository the simple copy repository
     * @param publicationLimit     the publication limit
     */
    @Autowired
    public BorrowVerificationService(BorrowRepository borrowRepository, SimpleCopyRepository simpleCopyRepository, @Value("${borrow.publication.limit}") Integer publicationLimit) {
        this.simpleCopyRepository = simpleCopyRepository;
        this.borrowRepository = borrowRepository;
        this.publicationLimit = publicationLimit;
    }

    /**
     * If user can borrow copy.
     *
     * If user is blocked.
     *
     * @param user the user
     * @return the boolean
     */
    @Transactional(readOnly = true)
    public boolean userCanBorrow(User user) {
        //todo: add more rule
        //example: distinguish how many book can an account borrow and how many audio?
        //or how many chinese can borrow and how many foreign can borrow.
        //maybe can use mongoDB to control the configuration of borrow book limit
        List<Borrow> hasBorrowed = this.borrowRepository.findAllByUserAndEnableIsTrue(user);
        return hasBorrowed.size() <= this.publicationLimit;
    }

    /**
     * If copy can borrow.
     *
     * Return true if copy's status is available.
     *
     * @param simpleCopy the simple copy
     * @return the boolean
     */
    @Transactional(readOnly = true)
    public boolean copyCanBorrow(SimpleCopy simpleCopy) {
        simpleCopy = this.simpleCopyRepository.findOne(simpleCopy.getId());
        return simpleCopy.getStatus() == CopyStatus.AVAILABLE;
    }


    /**
     * If copy can return.
     *
     * Return false if borrow is not enable(borrow is history)
     *
     * @param borrow the borrow
     * @return the boolean
     */
    @Transactional(readOnly = true)
    public boolean canReturn(Borrow borrow) {
        Objects.requireNonNull(borrow);
        if (!borrow.getEnable()) {
            return false;
        }
        Optional<Borrow> queriedBorrow = this.borrowRepository.findFirstByIdAndEnable(borrow.getId(), true);
        return queriedBorrow.isPresent();
    }
}
