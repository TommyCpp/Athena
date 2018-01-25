package com.athena.model.listener;

import com.athena.model.borrow.Borrow;
import com.athena.model.copy.CopyStatus;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by Tommy on 2017/10/31.
 */
public class BorrowListener {

    @PrePersist
    @PreUpdate
    public void setCopyStatus(Borrow borrow){
        if(borrow.getEnable()){
            //if is a valid Borrow
            borrow.getCopy().setStatus(CopyStatus.CHECKED_OUT);
        }
    }
}
