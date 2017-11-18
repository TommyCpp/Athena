package com.athena.util;

import com.athena.model.SimpleCopy;
import com.athena.repository.jpa.BorrowRepository;
import com.athena.security.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Tommy on 2017/11/18.
 */
@Component
public class PublicationDamagedHandler {
    private static Logger logger = LoggerFactory.getLogger(PublicationDamagedHandler.class);
    private BorrowRepository borrowRepository;

    @Autowired
    public PublicationDamagedHandler(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public void handleDamage(Account handler, SimpleCopy publicationCopy) {
        //todo:find last borrow
    }
}
