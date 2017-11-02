package com.athena.service.copy;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.exception.http.MixedCopyTypeException;
import com.athena.model.AbstractCopy;
import com.athena.service.ModelCRUDService;

import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/12.
 * <p>
 * Basic Copy operation. Use for SimpleCopy
 */
public interface GenericCopyService<T extends AbstractCopy> extends ModelCRUDService<T, Long> {
    /**
     * Add
     */
    @Override
    T add(T copy);

    @Override
    List<T> add(Iterable<T> copies);

    /**
     * Get
     */
    @Override
    T get(Long aLong) throws IdOfResourceNotFoundException, InvalidCopyTypeException;

    List<T> get(Iterable<Long> idList);

    /**
     * Delete
     * @param aLong
     */
    void deleteById(Long aLong);

    void deleteById(List<Long> copyLongList) throws MixedCopyTypeException;

    default void delete(T t){
        this.deleteById(t.getId());
    }


    /**
     * Update
     */
    @Override
    T update(T copy) throws IllegalEntityAttributeException;

    List<T> update(Iterable<T> copyList) throws IllegalEntityAttributeException;
}
