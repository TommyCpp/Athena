package com.athena.service;

import com.athena.annotation.ArgumentNotNull;
import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.ResourceNotDeletable;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.User;
import com.athena.repository.jpa.UserRepository;
import com.athena.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tommy on 2017/3/20.
 */
@Service
public class UserService implements ModelCRUDService<User, Long> {

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

    public List<User> get(Iterable<Long> ids) {
        return repository.findAll(ids);
    }

    @Override
    public User update(User user) throws ResourceNotFoundByIdException, IllegalEntityAttributeException {
        EntityUtil.requireEntityNotNull(user);
        if (!this.repository.exists(user.getId())) {
            throw new ResourceNotFoundByIdException();
        }
        return repository.save(user);
    }

    @Override
    @ArgumentNotNull
    public void delete(User user) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        repository.delete(user);
    }

    @Override
    @ArgumentNotNull
    public void delete(Iterable<User> users) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        repository.delete(users);
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
