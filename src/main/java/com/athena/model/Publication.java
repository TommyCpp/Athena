package com.athena.model;

import java.sql.Date;
import java.util.List;

/**
 * Created by Tommy on 2017/8/29.
 */
public interface Publication {
    String getTitle();

    void setTitle(String title);

    String getTitlePinyin();

    void setTitlePinyin(String pinyin);

    String getTitleShortPinyin();

    void setTitleShortPinyin(String pinyin);

    Publisher getPublisher();

    void setPublisher(Publisher publisher);

    List<? extends SimpleCopy> getCopies();

    Double getPrice();

    void setPrice(Double price);

    String getLanguage();

    void setLanguage(String language);

    Date getPublishDate();

    void setPublishDate(Date publishDate);

}
