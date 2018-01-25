package com.athena.model.message;

import com.athena.model.security.User;
import com.athena.repository.jpa.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * Created by Tommy on 2017/12/19.
 */
public class AuthorityUserGroup extends AbstractUserGroup {
    private GrantedAuthority authority;

    public AuthorityUserGroup(SimpleGrantedAuthority simpleGrantedAuthority) {
        this.authority = simpleGrantedAuthority;
    }

    @Override
    public List<User> fetchUsers(UserRepository repository) {
        return repository.findAllByIdentity(this.authority.getAuthority());
    }
}
