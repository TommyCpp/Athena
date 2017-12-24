package com.athena.model.domain.message;

import com.athena.model.User;
import com.athena.repository.jpa.UserRepository;

import java.util.List;

/**
 * Created by Tommy on 2017/12/19.
 */
abstract public class AbstractUserGroup {
    public abstract List<User> fetchUsers(UserRepository repository);
}
