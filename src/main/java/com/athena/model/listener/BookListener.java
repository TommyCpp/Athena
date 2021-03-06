package com.athena.model.listener;

import com.athena.model.publication.Book;
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
        this.setPublicationPinyin(book);
    }
}
