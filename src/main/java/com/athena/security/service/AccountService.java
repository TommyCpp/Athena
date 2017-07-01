package com.athena.security.service;

import com.athena.model.User;
import com.athena.repository.UserRepository;
import com.athena.security.exception.AccountNotFoundException;
import com.athena.security.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
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

    public Account loadAccountById(Long id) throws AuthenticationException{
        User user = userRepository.findOne(id);
        if(user == null){
            //Not found the user
            throw new AccountNotFoundException();
        }
        else{
            return new Account(user);

        }
    }
}
