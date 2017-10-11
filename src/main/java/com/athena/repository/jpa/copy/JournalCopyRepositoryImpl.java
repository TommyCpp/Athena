package com.athena.repository.jpa.copy;

import com.athena.model.Journal;
import com.athena.model.JournalCopy;
import com.athena.model.JournalPK;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
@Component
public class JournalCopyRepositoryImpl implements CopyRepositoryCustom<JournalCopy, JournalPK> {

    @PersistenceContext
    private final EntityManager em;

    public JournalCopyRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void update(JournalCopy journalCopy) {
        Query query = em.createNativeQuery("UPDATE journal_copy INNER JOIN copy ON copy.id=journal_copy.copy_id SET copy_id=?1,issn=?2,`index`=?3,year=?4,status=?5,created_date=?6,updated_date=?7 WHERE copy_id=?1");
        query.setParameter(1, journalCopy.getId());
        Journal journal = journalCopy.getJournal();
        query.setParameter(2, journal.getIssn());
        query.setParameter(3, journal.getIndex());
        query.setParameter(4, journal.getYear());
        query.setParameter(5, journalCopy.getStatus());
        query.setParameter(6, journalCopy.getCreatedDate());
        query.setParameter(7, journalCopy.getUpdatedDate());
        query.executeUpdate();
    }

    @Override
    public List<JournalCopy> isNotDeletable(JournalPK journalPK) {
        //todo: finish
        return null;
    }

}
