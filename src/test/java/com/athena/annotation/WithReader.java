package com.athena.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Tommy on 2017/5/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithReaderSecurityContextFactory.class)
public @interface WithReader {
    public String username() default "reader";

    public String password() default "123456";

}
