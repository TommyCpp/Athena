package com.athena.repository;

import com.athena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by tommy on 2017/3/18.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

}
