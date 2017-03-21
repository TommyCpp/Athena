package com.athena.security.model;

import com.athena.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 2017/3/21.
 */
public class JwtUserFactory {
    public JwtUserFactory() {

    }
    public static JwtUser create(User user) throws UsernameNotFoundException{
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getWechatId(),
                user.getEmail(),
                mapToGrantedAuthorities(user.getIdentity())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(String authority) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(authority));
        return list;
    }
}
