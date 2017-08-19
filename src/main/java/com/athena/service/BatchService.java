package com.athena.service;

import com.athena.exception.ResourceNotFoundException;
import com.athena.model.Batch;
import com.athena.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Tommy on 2017/8/17.
 */
@Service
public class BatchService {
    private final BatchRepository repository;

    @Autowired
    public BatchService(BatchRepository repository) {
        this.repository = repository;
    }

    public void createdBooks(Batch batch) {
        repository.save(batch);
    }

    public Batch findOne(String uuid) throws ResourceNotFoundException {
        Batch batch = this.repository.findOne(uuid);
        if (batch == null)
            throw new ResourceNotFoundException();
        return batch;
    }
}
