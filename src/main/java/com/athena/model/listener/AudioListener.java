package com.athena.model.listener;

import com.athena.model.Audio;
import com.github.stuxuhai.jpinyin.PinyinException;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by Tommy on 2017/11/23.
 */
public class AudioListener implements PublicationListener {

    @PrePersist
    @PreUpdate
    public void setPinyin(Audio audio) throws PinyinException {
        this.setPublicationPinyin(audio);
    }
}
