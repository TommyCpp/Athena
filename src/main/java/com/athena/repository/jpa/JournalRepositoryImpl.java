package com.athena.repository.jpa;

import com.athena.model.Journal;
import com.athena.model.JournalPK;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Journal> findByIdIn(List<JournalPK> journalPKs) {
        // create journalPKList
        List<String> journalPKsStringList = journalPKs.stream().map(o -> {
            StringBuilder builder = new StringBuilder("(");
            builder.append(o.getIssn());
            builder.append(o.getYear());
            builder.append(o.getIndex());
            return builder.toString();
        }).collect(Collectors.toList());
        Query query = em.createNativeQuery("SELECT * FROM journal WHERE (issn,year,`index`) IN (:pkSet)");
        query.setParameter("pkSet", StringUtils.join(journalPKsStringList.toArray(), ","));
        return (List<Journal>) query.getResultList();
    }
}
