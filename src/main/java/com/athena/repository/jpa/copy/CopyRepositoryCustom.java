package com.athena.repository.jpa.copy;

import com.athena.model.Copy;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
public interface CopyRepositoryCustom<T extends Copy, ID> {
    void update(T copy);

    List<T> isNotDeletable(ID id);

    @Transactional
    default void update(Iterable<T> copies) {
        StreamSupport.stream(copies.spliterator(), false).forEach(this::update);
    }

}