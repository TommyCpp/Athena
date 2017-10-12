package com.athena.repository.jpa.copy;

import com.athena.model.AudioCopy;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Tommy on 2017/9/24.
 */
public class AudioCopyRepositoryImpl implements CopyRepositoryCustom<AudioCopy,String> {

    @PersistenceContext
    private EntityManager em;

    @Value("${copy.status.isDeletable}")
    private String deletable;

    @Override
    public void update(AudioCopy copy) {
        Query query = em.createNativeQuery("UPDATE audio_copy INNER JOIN copy ON copy.id = audio_copy.copy_id SET copy_id=?1, isrc=?2,status = ?3, created_date = ?4, updated_date = ?5 WHERE copy_id = ?1");
        query.setParameter(1, copy.getId());
        query.setParameter(2, copy.getAudio().getIsrc());
        query.setParameter(3, copy.getStatus());
        query.setParameter(4, copy.getCreatedDate());
        query.setParameter(5, copy.getUpdatedDate());
        query.executeUpdate();
    }

    @Override
    public List<AudioCopy> isNotDeletable(String isrc) {
        //todo:user CopyQueryGenerator to generator SQL
        Query query = em.createNativeQuery("SELECT * FROM audio_copy INNER JOIN copy ON book_copy.copy_id = copy.id WHERE isrc=?1 AND status NOT IN (?2)");
        query.setParameter(1, isrc);
        query.setParameter(2, deletable);
        return query.getResultList();
    }
}
