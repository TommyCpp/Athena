package com.athena.model.domain.message;

import com.athena.model.User;
import org.springframework.data.domain.ExampleMatcher;

/**
 * Created by Tommy on 2017/12/19.
 */
abstract public class AbstractUserGroup {
    ExampleMatcher exampleMatcher;
    User example;

    public ExampleMatcher getExampleMatcher() {
        return exampleMatcher;
    }

    public User getExample() {
        return example;
    }
}
