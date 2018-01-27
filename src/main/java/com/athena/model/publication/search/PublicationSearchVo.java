package com.athena.model.publication.search;

import org.springframework.data.domain.Pageable;

/**
 * Created by Tommy on 2018/1/27.
 */
public interface PublicationSearchVo {

    String[] getTitles();

    void setTitles(String[] title);

    String getPublisherName();

    void setPublisherName(String publisherName);

    String getLanguage();

    void setLanguage(String language);

    Pageable getPageInfo();

    void setPageInfo(Pageable pageInfo);

}
