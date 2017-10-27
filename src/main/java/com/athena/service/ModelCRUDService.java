package com.athena.service;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.exception.http.ResourceNotDeletable;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Tommy on 2017/10/20.
 */
public interface ModelCRUDService<T, K extends Serializable> {
    T add(T t);

    default Iterable<T> add(Iterable<T> ts) {
        return StreamSupport.stream(ts.spliterator(), false).map(this::add).collect(Collectors.toList());
    }


    T get(K k) throws IdOfResourceNotFoundException, InvalidCopyTypeException;

    default Iterable<T> get(Iterable<K> pks) {
        return StreamSupport.stream(pks.spliterator(), false).map(k -> {
            try {
                return this.get(k);
            } catch (IdOfResourceNotFoundException | InvalidCopyTypeException e) {
                e.printStackTrace();//todo: error handle
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    T update(T t) throws IdOfResourceNotFoundException, IllegalEntityAttributeException;

    @Transactional
    default Iterable<T> update(Iterable<T> ts) throws IdOfResourceNotFoundException, IllegalEntityAttributeException {
        List<T> result = new ArrayList<>();
        for (T t : ts) {
            result.add(this.update(t));
        }
        return result;
    }

    void delete(T t) throws IdOfResourceNotFoundException, ResourceNotDeletable;

    @Transactional
    default void delete(Iterable<T> ts) throws IdOfResourceNotFoundException, ResourceNotDeletable{
        for(T t:ts){
            this.delete(t);
        }
    }

}
