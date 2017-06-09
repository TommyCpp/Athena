package com.athena.repository;

import com.athena.model.Copy;
import com.athena.model.CopyPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tommy on 2017/6/9.
 */
@Repository
public interface CopyRepository extends JpaRepository<Copy, CopyPK> {
    public Copy findOne(CopyPK id);
}
