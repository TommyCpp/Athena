package com.athena.annotation;

import java.lang.annotation.*;

/**
 * Created by Tommy on 2018/2/1.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PublicationSearchParam {
}
