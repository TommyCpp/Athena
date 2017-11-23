package com.athena.model.listener;

import com.athena.model.Publication;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * Created by Tommy on 2017/11/22.
 */
public interface PublicationListener<T extends Publication>{

    void setPinyin(T t) throws PinyinException ;

    default void setPublicationPinyin(Publication publication) throws PinyinException {
        if (publication.getLanguage().equals("Chinese")) {
            // if the publication is written in chinese then must set pinyin
            publication.setTitlePinyin(PinyinHelper.convertToPinyinString(publication.getTitle(), ",", PinyinFormat.WITHOUT_TONE));
            publication.setTitleShortPinyin(PinyinHelper.getShortPinyin(publication.getTitle()));
        }
    }
}
