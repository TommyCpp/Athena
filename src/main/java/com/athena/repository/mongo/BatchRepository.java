package com.athena.repository.mongo;

import com.athena.model.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Tommy on 2017/8/17.
 */
@Repository
public interface BatchRepository extends MongoRepository<Batch, String> {
    List<Batch> findByCreateAtBefore(Date date);

    Batch findByIdAndTypeIsIn(String id, String[] type);

    List<Batch> findByType(String type);
}
