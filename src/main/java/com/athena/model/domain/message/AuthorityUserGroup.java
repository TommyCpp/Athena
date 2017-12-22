package com.athena.model.domain.message;

import com.athena.model.User;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Created by Tommy on 2017/12/19.
 */
public class AuthorityUserGroup extends AbstractUserGroup {
    public AuthorityUserGroup(SimpleGrantedAuthority simpleGrantedAuthority) {
        this.example = new User();
        this.example.setIdentity(simpleGrantedAuthority.getAuthority());
        this.exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase();
    }
}
