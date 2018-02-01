package com.athena.annotation;

import java.lang.annotation.*;

/**
 * Created by Tommy on 2018/2/1.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PublicationSearchParam {
    /**
     * parameter keys.
     *
     * @return keys
     * @see com.athena.util.publication.search.PublicationSearchParamResolver
     */
    String[] values() default {"title", "publisherName", "language", "page", "count", "lastCursor"};
}
