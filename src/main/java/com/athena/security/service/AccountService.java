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
public class AccountService {

    @Autowired
    private
    UserRepository userRepository;

    public Account loadAccountById(Long id) {
        return new Account(userRepository.findOne(id));
    }
}
