package com.athena.model.publication.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by Tommy on 2018/3/18.
 */
abstract class AbstractPublicationSearchVo<T> implements PublicationSearchVo<T> {
    protected String[] title;
    protected String publisherName;
    protected String[] author;
    protected String language;
    protected Integer count;
    protected Integer page;
    protected Integer lastCursor;

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public Integer getPage() {
        return page;
    }

    @Override
    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public Integer getLastCursor() {
        return lastCursor;
    }

    @Override
    public void setLastCursor(Integer lastCursor) {
        this.lastCursor = lastCursor;
    }

    @Override
    @JsonProperty("title")
    public String[] getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String[] title) {
        this.title = title;
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
    @JsonIgnore
    public Pageable getPageable() {
        Integer startPage = 1;
        if (page != null) {
            startPage = page;
        } else if (lastCursor != null) {
            startPage = lastCursor / count;
        }
        return new PageRequest(startPage - 1, count);
    }
}
