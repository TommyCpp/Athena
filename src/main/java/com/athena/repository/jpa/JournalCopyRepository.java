package com.athena.repository.jpa;

import com.athena.model.JournalCopy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Tommy on 2017/8/30.
 */
public interface JournalCopyRepository extends JpaRepository<JournalCopy, Long> {
    List<JournalCopy> findByIdIsInAndJournalIsNotNull(List<Long> ids);
}
