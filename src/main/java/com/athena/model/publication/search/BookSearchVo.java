package com.athena.model.publication.search;

import org.springframework.data.domain.Pageable;

/**
 * Created by Tommy on 2018/1/27.
 */
public class BookSearchVo implements PublicationSearchVo {
    private String[] titles;
    private String publisherName;
    private String language;
    private Pageable pageInfo;

    @Override
    public String[] getTitles() {
        return this.titles;
    }

    @Override
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    @Override
    public String getPublisherName() {
        return this.publisherName;
    }

    @Override
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public String getLanguage() {
        return this.language;
    }

    @Override
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public Pageable getPageInfo() {
        return pageInfo;
    }

    @Override
    public void setPageInfo(Pageable pageInfo) {
        this.pageInfo = pageInfo;
    }


}
