package com.athena.model.publication.search;

import com.athena.model.publication.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * Created by Tommy on 2018/1/27.
 */
public class BookSearchVo implements PublicationSearchVo<Book> {
    private String[] titles;
    private String publisherName;
    private String language;

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

    private Integer count;
    private Integer page;
    private Integer lastCursor;


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
    @JsonIgnore
    public Specification<Book> getSpecification() {
        Specification<Book> titlesIn = this.getDefaultSpecifications();
        Specification<Book> publisherNameIs = this.getDefaultSpecifications();
        Specification<Book> languageIs = this.getDefaultSpecifications();
        if (this.titles != null) {
            titlesIn = (root, criteriaQuery, criteriaBuilder) -> root.get("title").in((Object[]) titles);
        }
        if (this.publisherName != null) {
            publisherNameIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("publisher").get("name"), publisherName);
        }
        if (this.language != null) {
            languageIs = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("language"), language);
        }

        return Specifications.where(titlesIn).and(publisherNameIs).and(languageIs);
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
