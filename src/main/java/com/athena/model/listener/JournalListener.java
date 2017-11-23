package com.athena.model.listener;

import com.athena.model.Journal;
import com.github.stuxuhai.jpinyin.PinyinException;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by Tommy on 2017/11/23.
 */
public class JournalListener implements PublicationListener {

    @PrePersist
    @PreUpdate
    public void setPinyin(Journal journal) throws PinyinException {
        this.setPublicationPinyin(journal);
    }

}
