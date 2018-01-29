package com.athena.model.search;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


/**
 * Created by Tommy on 2018/1/29.
 */
public interface SearchVo {
    Specification getSpecification();

    Pageable getPageable();
}
