package com.athena.service;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.IllegalEntityAttributeExcpetion;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.exception.MixedCopyTypeException;
import com.athena.model.Copy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/12.
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
    void updateCopy(T copy) throws IllegalEntityAttributeExcpetion;

    void updateCopies(List<T> copyList) throws IllegalEntityAttributeExcpetion;
}
