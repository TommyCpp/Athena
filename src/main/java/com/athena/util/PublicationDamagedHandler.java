package com.athena.util;

import com.athena.model.Borrow;
import com.athena.model.CopyStatus;
import com.athena.model.SimpleCopy;
import com.athena.repository.jpa.BorrowRepository;
import com.athena.security.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

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
        Borrow lastBorrow = this.borrowRepository.findFirstByCopyAndEnableIsFalseOrderByUpdatedDateDesc(publicationCopy);
        if (lastBorrow == null) {
            //if no borrow correspond to publicationCopy
            publicationCopy.setStatus(CopyStatus.AVAILABLE);
            logger.error("{}----{}---- Copy has not been borrowed", Calendar.getInstance().toString(),handler.toString());
        }
        else{
            //todo:send email
        }
    }
}
