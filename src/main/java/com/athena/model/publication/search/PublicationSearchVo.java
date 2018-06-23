package com.athena.model.publication.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Tommy on 2018/1/27.
 */
public interface PublicationSearchVo<T> extends SearchVo {

    Integer getCount();

    void setCount(Integer count);

    Integer getPage();

    void setPage(Integer page);

    Integer getLastCursor();

    void setLastCursor(Integer lastCursor);

    String[] getTitle();

    void setTitle(String[] title);

    String getPublisherName();

    void setPublisherName(String publisherName);

    String getLanguage();

    void setLanguage(String language);

    @Override
    default Pageable getPageable() {
        Integer startPage = 1;
        if (getPage() != null) {
            startPage = getPage();
        } else if (getLastCursor() != null) {
            startPage = getLastCursor() / getCount();
        }
        return new PageRequest(startPage - 1, getCount());
    }

    @JsonIgnore
    default Specification<T> getDefaultSpecifications() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(Boolean.TRUE));
    }

}
