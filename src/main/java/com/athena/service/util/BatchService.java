package com.athena.service.util;

import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.exception.http.ResourceNotDeletable;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.common.Batch;
import com.athena.repository.mongo.BatchRepository;
import com.athena.service.ModelCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tommy on 2017/8/17.
 */
@Service
public class BatchService implements ModelCRUDService<Batch, String> {
    private BatchRepository repository;

    @Autowired
    public BatchService(BatchRepository repository) {
        this.repository = repository;
    }

    public Batch add(Batch batch) {
        return repository.save(batch);
    }

    @Override
    public Batch get(String uuid) throws ResourceNotFoundByIdException, InvalidCopyTypeException {
        Batch batch = this.repository.findOne(uuid);
        if (batch == null) {
            throw new ResourceNotFoundByIdException();
        }
        return batch;
    }

    @Override
    public Batch update(Batch batch) throws ResourceNotFoundByIdException, IllegalEntityAttributeException {
        return this.repository.save(batch);
    }

    @Override
    public void delete(Batch batch) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        this.repository.delete(batch);
    }

    public Batch get(String uuid, String identity) throws ResourceNotFoundByIdException {
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

    public Batch add(List<String> urls, String type) {
        Batch batch = new Batch(UUID.randomUUID().toString(), type, Calendar.getInstance().getTime(), urls);
        batch = this.add(batch);
        return batch;
    }

    public Batch add(List<String> urls, Class type){
        return this.add(urls, type.getSimpleName());
    }

}
