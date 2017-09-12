package com.athena.service;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.IllegalEntityAttributeExcpetion;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.exception.MixedCopyTypeException;
import com.athena.model.Copy;

import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
interface CopyService<T extends Copy> {
    /**
     * Add
     */
    void addCopy(T copy);

    void addCopies(List<T> copies);


    /**
     * Get
     * */
    T getCopy(Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException;

    List<T> getCopies(List<Long> idList);


    /**
     * Delete
     * */
    void deleteCopy(Long id);

    void deleteCopies(List<Long> copyIdList) throws MixedCopyTypeException;


    /**
     * Update
     * */
    void updateCopy(T copy) throws IllegalEntityAttributeExcpetion;

    void updateCopies(List<T> copyList) throws IllegalEntityAttributeExcpetion;
}
