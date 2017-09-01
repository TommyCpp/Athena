package com.athena.repository.jpa;

import com.athena.model.SimpleCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tommy on 2017/6/9.
 */
@Repository
public interface SimpleCopyRepository extends JpaRepository<SimpleCopy, Long> {

}
