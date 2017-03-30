package com.athena.security.service;

import com.athena.repository.UserRepository;
import com.athena.security.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tommy on 2017/3/24.
 */
@Service
public class AccountService {

    private final
    UserRepository userRepository;

    @Autowired
    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Account loadAccountById(Long id) {
        return new Account(userRepository.findOne(id));
    }
}
