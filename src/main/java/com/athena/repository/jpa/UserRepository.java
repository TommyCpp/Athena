package com.athena.repository.jpa;

import com.athena.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by tommy on 2017/3/18.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByIdentity(String identity);
}
