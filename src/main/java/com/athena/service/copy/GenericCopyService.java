package com.athena.service.copy;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.exception.http.MixedCopyTypeException;
import com.athena.model.Copy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/12.
 *
 * Basic Copy operation. Use for SimpleCopy
 */
public interface GenericCopyService<T extends Copy, ID extends Serializable> {
    /**
     * Add
     */
    void addCopy(T copy);

    void addCopies(List<T> copies);

    /**
     * Get
     */
    T getCopy(ID id) throws IdOfResourceNotFoundException, InvalidCopyTypeException;

    List<T> getCopies(List<ID> idList);

    /**
     * Delete
     */
    void deleteCopy(Long id);

    void deleteCopies(List<Long> copyIdList) throws MixedCopyTypeException;


    /**
     * Update
     */
    void updateCopy(T copy) throws IllegalEntityAttributeException;

    void updateCopies(List<T> copyList) throws IllegalEntityAttributeException;
}
