package com.athena.model.publication.search;

import com.athena.model.publication.Book;
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
    public Specification<Book> getSpecification() {
        //todo: test
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
    public Pageable getPageable() {
        return pageInfo;
    }

    @Override
    public void setPageable(Pageable pageInfo) {
        this.pageInfo = pageInfo;
    }


}
