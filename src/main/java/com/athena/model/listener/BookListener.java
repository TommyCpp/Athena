package com.athena.model.listener;

import com.athena.model.Book;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by Tommy on 2017/9/28.
 */
public class BookListener {

    @PrePersist
    public void setPinyin(Book book) throws PinyinException {
        if (book.getLanguage().equals("Chinese")) {
            // if the book is written in chinese then must set pinyin
            book.setTitlePinyin(PinyinHelper.convertToPinyinString(book.getTitle(), ",", PinyinFormat.WITHOUT_TONE));
            book.setTitleShortPinyin(PinyinHelper.getShortPinyin(book.getTitle()));
        }
    }

    @PreUpdate
    public void setPinyinIfNot(Book book) throws PinyinException {
        if (book.getLanguage().equals("Chinese")) {
            if (book.getTitlePinyin() == null || book.getTitlePinyin().length() == 0) {
                book.setTitlePinyin(PinyinHelper.convertToPinyinString(book.getTitle(), ",", PinyinFormat.WITHOUT_TONE));
            }
            if (book.getTitleShortPinyin() == null || book.getTitleShortPinyin().length() == 0) {
                book.setTitleShortPinyin(PinyinHelper.getShortPinyin(book.getTitle()));
            }
        }
    }

}
