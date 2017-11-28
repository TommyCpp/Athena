package com.athena.service;

import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.exception.http.ResourceNotFoundException;
import com.athena.model.Batch;
import com.athena.repository.mongo.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public void save(Batch batch) {
        repository.save(batch);
    }

    public Batch findOne(String uuid) throws ResourceNotFoundException {
        Batch batch = this.repository.findOne(uuid);
        if (batch == null)
            throw new ResourceNotFoundException();
        return batch;
    }

    public Batch findOne(String uuid, String identity) throws ResourceNotFoundByIdException {
        List<String> types = new ArrayList<>();
        if (identity.equals("ROLE_ADMIN")) {
            types.add("Book");
        }
        //todo: to add more situation in which different roles have access of different resource
        // e.g: the reader may access a batch of *Borrow* resource
        Batch batch = this.repository.findByIdAndTypeIsIn(uuid, types.size() > 0 ? (String[]) types.toArray() : new String[0]);
        if (batch == null) {
            throw new ResourceNotFoundByIdException();
        }
        return batch;
    }
}
