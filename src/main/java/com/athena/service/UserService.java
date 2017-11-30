package com.athena.service;

import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.ResourceNotDeletable;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.User;
import com.athena.repository.jpa.UserRepository;
import com.athena.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tommy on 2017/3/20.
 */
@Service
public class UserService implements ModelCRUDService<User,Long>{

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
    public User get(Long id) {
        return repository.findOne(id);
    }

    @Override
    public User update(User user) throws ResourceNotFoundByIdException, IllegalEntityAttributeException {
        EntityUtil.requireEntityNotNull(user);
        EntityUtil.requireEntityNotNull(repository.findOne(user.getId()));
        return repository.save(user);
    }

    @Override
    public void delete(User user) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        repository.delete(user);
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    public User get(int id) {
        return repository.findOne(new Long(id));
    }

    /**
     * Save.
     *
     * @param user the user
     */
    public User add(User user) {
        return repository.save(user);
    }
}
