package com.athena.repository.jpa.copy;

import com.athena.model.copy.AudioCopy;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2017/9/24.
 */
public class AudioCopyRepositoryImpl implements CopyRepositoryCustom<AudioCopy, String> {

    @PersistenceContext
    private EntityManager em;

    @Value("${copy.status.isDeletable}")
    private String deletable;

    @Override
    public AudioCopy update(AudioCopy copy) {
        Query query = em.createNativeQuery("UPDATE audio_copy INNER JOIN copy ON copy.id = audio_copy.copy_id SET copy_id=?1, isrc=?2,status = ?3, created_date = ?4, updated_date = ?5 WHERE copy_id = ?1");
        query.setParameter(1, copy.getId());
        query.setParameter(2, copy.getAudio().getIsrc());
        query.setParameter(3, copy.getStatus());
        query.setParameter(4, copy.getCreatedDate());
        query.setParameter(5, copy.getUpdatedDate());
        query.executeUpdate();
        em.flush();
        return copy;
    }

    @Override
    public List<AudioCopy> isNotDeletable(String isrc) {
        String[] deletableStrings = this.deletable.split(",");
        List<Integer> deletableInt = new ArrayList<>(deletableStrings.length);
        for (int i = 0; i < deletableStrings.length; i++) {
            deletableInt.add(Integer.valueOf(deletableStrings[i]));
        }
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(AudioCopy.class);
        Root target = criteriaQuery.from(AudioCopy.class);
        criteriaQuery.where(
                builder.and(
                        builder.not(target.get("status").in(deletableStrings)),
                        builder.equal(target.get("audio").get("isrc"), isrc)
                )
        );
        Query query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
