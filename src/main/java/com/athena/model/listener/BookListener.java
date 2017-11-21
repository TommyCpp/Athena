package com.athena.model.listener;

import com.athena.model.Book;
import com.athena.model.Publication;
import com.github.stuxuhai.jpinyin.PinyinException;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by Tommy on 2017/9/28.
 */
public class BookListener implements PublicationListener {

    @PrePersist
    @PreUpdate
    public void setPinyin(Book book) throws PinyinException {
        this.setPinyin((Publication)book);
    }
}
