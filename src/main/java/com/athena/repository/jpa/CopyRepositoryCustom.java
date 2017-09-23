package com.athena.repository.jpa;

import com.athena.model.Copy;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
public interface CopyRepositoryCustom<T extends Copy> {
    void update(T copy);

    void update(Iterable<T> copies);
}
