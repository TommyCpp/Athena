package com.athena.service.copy;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.model.Copy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 吴钟扬 on 2017/9/12.
 *
 * Copy operation for BookCopy,JournalCopy...
 */
interface CopyService<T extends Copy, ID extends Serializable, FK extends Serializable> extends GenericCopyService<T, ID> {

    List<T> getCopies(FK fkList) throws IdOfResourceNotFoundException;

    void deleteCopies(FK fk) throws IdOfResourceNotFoundException;

}