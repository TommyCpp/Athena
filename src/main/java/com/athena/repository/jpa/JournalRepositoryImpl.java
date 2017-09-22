package com.athena.repository.jpa;

import com.athena.model.JournalCopy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
@Repository
public class JournalRepositoryImpl implements JournalRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;

    public JournalRepositoryImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public void update(JournalCopy journalCopy) {
        //todo: update only the JournalCopy
    }
}
