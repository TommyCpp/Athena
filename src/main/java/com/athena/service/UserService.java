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

    /**
     * Instantiates a new User service.
     *
     * @param repository the repository
     */
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    public User getUser(Long id) {
        return repository.findOne(id);
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    public User getUser(int id) {
        return repository.findOne(new Long(id));
    }

    /**
     * Save.
     *
     * @param user the user
     */
    public void save(User user) {
        repository.save(user);
    }

    /**
     * Find by id user.
     *
     * @param _id the id
     * @return the user
     * @throws UsernameNotFoundException the username not found exception
     */
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
