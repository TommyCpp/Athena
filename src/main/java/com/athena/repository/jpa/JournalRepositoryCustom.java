package com.athena.repository.jpa;

import com.athena.model.JournalPK;

import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/12.
 */
public interface JournalRepositoryCustom {
    List findByIdIn(List<JournalPK> journalPKs);
}
