package com.athena.model.domain.message;

import com.athena.model.User;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2017/12/19.
 */
public class AuthorityUserGroup extends AbstractUserGroup {
    public AuthorityUserGroup(SimpleGrantedAuthority simpleGrantedAuthority) {
        this.example = new User();
        List<String> identities = new ArrayList<>();
        identities.add(simpleGrantedAuthority.getAuthority());
        this.example.setIdentity(identities);
        this.exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase();
    }
}
