package com.athena.model;

import java.util.List;

/**
 * Created by Tommy on 2017/8/29.
 */
public interface Publication {
    String getTitle();

    void setTitle(String title);

    String getTitlePinyin();

    Publisher getPublisher();

    void setPublisher(Publisher publisher);

    List<? extends Copy> getCopies();

    Double getPrice();

    void setPrice(Double price);

    String getLanguage();

    void setLanguage(String language);

}
