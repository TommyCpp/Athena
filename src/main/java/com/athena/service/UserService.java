package com.athena.service;

import com.athena.model.User;
import com.athena.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by tommy on 2017/3/20.
 */
@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUser(Long id) {
        return repository.findOne(id);
    }

    public User getUser(int id) {
        return repository.findOne(new Long(id));
    }

    public void save(User user) {
        repository.save(user);
    }

    public User findById(String _id) throws UsernameNotFoundException {
        Long id = Long.valueOf(_id);
        User user = repository.findOne(id);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("no such user");
        }
    }
}
