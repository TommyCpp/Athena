package com.athena.model.publication.search;

import com.athena.model.search.SearchVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Tommy on 2018/1/27.
 */
public interface PublicationSearchVo<T> extends SearchVo {

    String[] getTitles();

    void setTitles(String[] title);

    String getPublisherName();

    void setPublisherName(String publisherName);

    String getLanguage();

    void setLanguage(String language);

    void setPageable(Pageable pageInfo);

    default Specification<T> getDefaultSpecifications() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(Boolean.TRUE));
    }

}
