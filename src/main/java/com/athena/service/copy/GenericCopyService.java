package com.athena.service.copy;

import com.athena.exception.http.*;
import com.athena.exception.internal.CannotPartialUpdateIdFieldException;
import com.athena.exception.internal.EntityAttributeNotFoundException;
import com.athena.model.domain.copy.AbstractCopy;
import com.athena.service.ModelCRUDService;

import java.util.List;
import java.util.Map;

import static com.athena.util.EntityUtil.partialUpdateEntity;

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
    T get(Long aLong) throws ResourceNotFoundByIdException, InvalidCopyTypeException;

    List<T> get(Iterable<Long> idList);

    /**
     * Delete
     *
     * @param id
     */
    void deleteById(Long id);

    void deleteById(List<Long> copyLongList) throws MixedCopyTypeException;

    default void delete(T t) {
        this.deleteById(t.getId());
    }


    /**
     * Update
     */
    @Override
    T update(T copy) throws IllegalEntityAttributeException;

    List<T> update(Iterable<T> copyList) throws IllegalEntityAttributeException;

    default T partialUpdate(Long copyId, Map<String, Object> params) throws  ResourceNotFoundException, InvalidCopyTypeException, IllegalEntityAttributeException {
        T copy = this.get(copyId);
        try {
            partialUpdateEntity(copy, params.entrySet());
        } catch (EntityAttributeNotFoundException e) {
            e.printStackTrace();
        } catch (CannotPartialUpdateIdFieldException e) {
            throw new IllegalEntityAttributeException();
        }
        return this.update(copy);
    }
}
