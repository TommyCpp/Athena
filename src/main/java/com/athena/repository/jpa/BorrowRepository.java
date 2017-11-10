package com.athena.repository.jpa;

import com.athena.model.Borrow;
import com.athena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Tommy on 2017/11/5.
 */
@Repository
public interface BorrowRepository extends JpaRepository<Borrow, String>, BorrowRepositoryCustom {

    List<Borrow> findAllByUserAndEnableIsTrue(User user);

}
