package com.athena.repository.jpa;

import com.athena.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Tommy on 2017/11/5.
 */
public interface BorrowRepository extends JpaRepository<Borrow, String>, BorrowRepositoryCustom {

}
