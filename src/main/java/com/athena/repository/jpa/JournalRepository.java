package com.athena.repository.jpa;

import com.athena.model.Journal;
import com.athena.model.JournalPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tommy on 2017/8/28.
 */
@Repository
public interface JournalRepository extends JpaRepository<Journal, JournalPK> {
}
