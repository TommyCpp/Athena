package com.athena.repository.jpa.copy;

import com.athena.model.Journal;
import com.athena.model.JournalCopy;
import com.athena.model.JournalPK;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
@Component
public class JournalCopyRepositoryImpl implements CopyRepositoryCustom<JournalCopy, JournalPK> {

    @PersistenceContext
    private final EntityManager em;

    @Value("${copy.status.isDeletable}")
    private String deletable;

    public JournalCopyRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public JournalCopy update(JournalCopy copy) {
        Query query = em.createNativeQuery("UPDATE journal_copy INNER JOIN copy ON copy.id=journal_copy.copy_id SET copy_id=?1,issn=?2,issue=?3,year=?4,status=?5,created_date=?6,updated_date=?7 WHERE copy_id=?1");
        query.setParameter(1, copy.getId());
        Journal journal = copy.getJournal();
        query.setParameter(2, journal.getIssn());
        query.setParameter(3, journal.getIssue());
        query.setParameter(4, journal.getYear());
        query.setParameter(5, copy.getStatus());
        query.setParameter(6, copy.getCreatedDate());
        query.setParameter(7, copy.getUpdatedDate());
        query.executeUpdate();
        em.flush();
        return copy;
    }

    @Override
    public List<JournalCopy> isNotDeletable(JournalPK journalPK) {
        String[] deletableStrings = this.deletable.split(",");
        List<Integer> deletableInt = new ArrayList<>(deletableStrings.length);
        for (int i = 0; i < deletableStrings.length; i++) {
            deletableInt.add(Integer.valueOf(deletableStrings[i]));
        }
        return this.isNotDeletable(JournalCopy.class, journalPK, this.em, deletableInt);
    }

}
