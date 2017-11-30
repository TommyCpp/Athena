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
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 */
public interface ModelCRUDService<T, K extends Serializable> {
    /**
     * Add t.
     *
     * @param t the t
     * @return the t
     */
    T add(T t);

    /**
     * Add iterable.
     *
     * @param ts the ts
     * @return the iterable
     */
    default Iterable<T> add(Iterable<T> ts) {
        return StreamSupport.stream(ts.spliterator(), false).map(this::add).collect(Collectors.toList());
    }


    /**
     * Get t.
     *
     * @param k the k
     * @return the t
     * @throws ResourceNotFoundByIdException the resource not found by id exception
     * @throws InvalidCopyTypeException      the invalid copy type exception
     */
    T get(K k) throws ResourceNotFoundByIdException, InvalidCopyTypeException;

    /**
     * Get iterable.
     *
     * @param pks the pks
     * @return the iterable
     * @throws InvalidCopyTypeException      the invalid copy type exception
     * @throws ResourceNotFoundByIdException the resource not found by id exception
     */
    default Iterable<T> get(Iterable<K> pks) throws InvalidCopyTypeException, ResourceNotFoundByIdException {
        List<T> result = new ArrayList<>();
        for (K pk : pks) {
            result.add(this.get(pk));
        }
        return result.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * Update t.
     *
     * @param t the t
     * @return the t
     * @throws ResourceNotFoundByIdException   the resource not found by id exception
     * @throws IllegalEntityAttributeException the illegal entity attribute exception
     */
    T update(T t) throws ResourceNotFoundByIdException, IllegalEntityAttributeException;

    /**
     * Update iterable.
     *
     * @param ts the ts
     * @return the iterable
     * @throws ResourceNotFoundByIdException   the resource not found by id exception
     * @throws IllegalEntityAttributeException the illegal entity attribute exception
     */
    @Transactional
    default Iterable<T> update(Iterable<T> ts) throws ResourceNotFoundByIdException, IllegalEntityAttributeException {
        List<T> result = new ArrayList<>();
        for (T t : ts) {
            result.add(this.update(t));
        }
        return result;
    }

    /**
     * Delete.
     *
     * @param t the t
     * @throws ResourceNotFoundByIdException the resource not found by id exception
     * @throws ResourceNotDeletable          the resource not deletable
     */
    void delete(T t) throws ResourceNotFoundByIdException, ResourceNotDeletable;

    /**
     * Delete.
     *
     * @param ts the ts
     * @throws ResourceNotFoundByIdException the resource not found by id exception
     * @throws ResourceNotDeletable          the resource not deletable
     */
    @Transactional
    default void delete(Iterable<T> ts) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        for (T t : ts) {
            this.delete(t);
        }
    }

    /**
     * Delete.
     *
     * @param id the id
     * @throws ResourceNotFoundByIdException the resource not found by id exception
     * @throws ResourceNotDeletable          the resource not deletable
     * @throws InvalidCopyTypeException      the invalid copy type exception
     */
    default void delete(K id) throws ResourceNotFoundByIdException, ResourceNotDeletable, InvalidCopyTypeException {
        T obj = this.get(id);
        if (Objects.isNull(obj)) {
            throw new ResourceNotFoundByIdException();
        }
        this.delete(obj);
    }

}
