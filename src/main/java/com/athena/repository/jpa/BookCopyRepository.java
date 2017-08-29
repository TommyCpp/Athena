package com.athena.repository.jpa;

import com.athena.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Tommy on 2017/8/30.
 */
@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
}
