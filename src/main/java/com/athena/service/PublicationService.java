package com.athena.service;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.exception.http.ResourceNotDeletable;
import com.athena.model.Publication;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Tommy on 2017/10/1.
 */
public interface PublicationService<T extends Publication, K extends Serializable> extends ModelCRUDService<T, K>{
    T get(K k);

    List<T> get(List<K> ks);

    T update(T t) throws IdOfResourceNotFoundException;

    @Transactional
    List<T> update(List<T> ts) throws IdOfResourceNotFoundException;

    void delete(T t) throws IdOfResourceNotFoundException, ResourceNotDeletable;

    @Transactional
    void delete(List<T> ts) throws IdOfResourceNotFoundException, ResourceNotDeletable;

    List<T> add(Iterable<T> ts);
}
