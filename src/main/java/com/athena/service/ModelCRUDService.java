package com.athena.service;

import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.exception.http.ResourceNotDeletable;
import com.athena.exception.http.ResourceNotFoundByIdException;
import org.springframework.transaction.annotation.Transactional;

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


    T get(K k) throws ResourceNotFoundByIdException, InvalidCopyTypeException;

    default Iterable<T> get(Iterable<K> pks) throws InvalidCopyTypeException, ResourceNotFoundByIdException {
        List<T> result = new ArrayList<>();
        for (K pk : pks) {
            result.add(this.get(pk));
        }
        return result.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }

    T update(T t) throws ResourceNotFoundByIdException, IllegalEntityAttributeException;

    @Transactional
    default Iterable<T> update(Iterable<T> ts) throws ResourceNotFoundByIdException, IllegalEntityAttributeException {
        List<T> result = new ArrayList<>();
        for (T t : ts) {
            result.add(this.update(t));
        }
        return result;
    }

    void delete(T t) throws ResourceNotFoundByIdException, ResourceNotDeletable;

    @Transactional
    default void delete(Iterable<T> ts) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        for (T t : ts) {
            this.delete(t);
        }
    }

    default void delete(K id) throws ResourceNotFoundByIdException, ResourceNotDeletable, InvalidCopyTypeException {
        T obj = this.get(id);
        if (Objects.isNull(obj)) {
            throw new ResourceNotFoundByIdException();
        }
        this.delete(obj);
    }

}
