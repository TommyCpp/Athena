package com.athena.repository;

import com.athena.model.Book;
import com.athena.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tommy on 2017/3/28.
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, String> {
    Publisher findOne(String id);
    Publisher findPublisherByName(String name);
}
