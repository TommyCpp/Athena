package com.athena.security.service;

import com.athena.security.model.JwtUserFactory;
import com.athena.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by tommy on 2017/3/21.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private
    UserService userService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return JwtUserFactory.create(userService.findById(s));
    }
}
