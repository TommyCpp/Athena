package com.athena.repository.jpa.copy;

import com.athena.model.Journal;
import com.athena.model.JournalCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Tommy on 2017/8/30.
 */
@Repository
public interface JournalCopyRepository extends JpaRepository<JournalCopy, Long>, CopyRepositoryCustom<JournalCopy> {
    List<JournalCopy> findByIdIsInAndJournalIsNotNull(List<Long> ids);

    JournalCopy findByIdAndJournalIsNotNull(Long id);

    List<JournalCopy> findByJournal(Journal journal);
}
