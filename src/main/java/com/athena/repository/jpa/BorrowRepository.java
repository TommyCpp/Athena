package com.athena.repository.jpa;

import com.athena.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tommy on 2017/11/5.
 */
@Repository
public interface BorrowRepository extends JpaRepository<Borrow, String>, BorrowRepositoryCustom {

}
