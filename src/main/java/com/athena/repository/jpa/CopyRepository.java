package com.athena.repository.jpa;

import com.athena.model.Book;
import com.athena.model.Copy;
import com.athena.model.CopyPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Tommy on 2017/6/9.
 */
@Repository
public interface CopyRepository extends JpaRepository<Copy, CopyPK> {
    Copy findOne(CopyPK id);

    List<Copy> getCopiesByBook(Book book);
}
