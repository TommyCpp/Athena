package com.athena.repository.jpa;

import com.athena.model.Borrow;
import com.athena.model.SimpleCopy;
import com.athena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Tommy on 2017/11/5.
 */
@Repository
public interface BorrowRepository extends JpaRepository<Borrow, String>, BorrowRepositoryCustom {

    List<Borrow> findAllByUserAndEnableIsTrue(User user);

    Optional<Borrow> findFirstByIdAndEnable(String id, Boolean status);

    Borrow findFirstByCopyOrderByUpdatedDate(SimpleCopy simpleCopy);//todo:test

}
