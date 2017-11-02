package com.athena.repository.jpa.copy;

import com.athena.model.SimpleCopy;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
public interface CopyRepositoryCustom<T extends SimpleCopy, ID> {
    T update(T copy);

    List<T> isNotDeletable(ID id);

    @Transactional
    default List<T> update(Iterable<T> copies) {
        return StreamSupport.stream(copies.spliterator(), false).map(this::update).collect(Collectors.toList());
    }

    default List<T> isNotDeletable(Class<? extends SimpleCopy> targetClass, ID id, EntityManager em, List<Integer> deletableStatus) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(targetClass);
        Root target = criteriaQuery.from(targetClass);
        criteriaQuery.where(builder.not(target.get("status").in(deletableStatus)));
        Query query = em.createQuery(criteriaQuery);
        return query.getResultList();

    }
}