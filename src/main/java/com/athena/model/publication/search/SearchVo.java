package com.athena.model.publication.search;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


/**
 * Created by Tommy on 2018/1/29.
 */
public interface SearchVo {
    /**
     * Gets specification according to the property.
     *
     * @return the specification
     */
    Specification getSpecification();

    /**
     * Gets pageable.
     *
     * @return the pageable
     */
    Pageable getPageable();
}
