package com.athena.security.service;

import com.athena.model.User;
import com.athena.repository.UserRepository;
import com.athena.security.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by tommy on 2017/3/24.
 */
public class AccountService implements UserDetailsService {

    @Autowired
    private
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Long id = Long.valueOf(s);
        return loadUserById(id);
    }

    private UserDetails loadUserById(Long id) {
        User user = userRepository.findOne(id);
        return new Account(user);
    }
}
