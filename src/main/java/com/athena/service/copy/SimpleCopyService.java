package com.athena.service.copy;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.model.SimpleCopy;
import com.athena.repository.jpa.copy.SimpleCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Tommy on 2017/8/24.
 */
@Service
public class SimpleCopyService implements GenericCopyService<SimpleCopy, Long> {
    private final SimpleCopyRepository simpleCopyRepository;

    @Autowired
    public SimpleCopyService(SimpleCopyRepository simpleCopyRepository) {
        this.simpleCopyRepository = simpleCopyRepository;
    }


    @Override
    public void addCopy(SimpleCopy copy) {
        this.simpleCopyRepository.save(copy);
    }

    @Override
    public void addCopies(List<SimpleCopy> copies) {
        this.simpleCopyRepository.save(copies);
    }

    @Override
    public SimpleCopy getCopy(Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        SimpleCopy copy = this.simpleCopyRepository.findOne(id);
        if (copy == null) {
            throw new IdOfResourceNotFoundException();
        }
        return copy;
    }

    @Override
    public List<SimpleCopy> getCopies(List<Long> idList) {
        return this.simpleCopyRepository.findAll(idList);
    }


    @Override
    public void deleteCopy(Long id) {
        this.simpleCopyRepository.delete(id);
    }

    @Override
    public void deleteCopies(List<Long> copyIdList) {
        List<SimpleCopy> simpleCopies = this.simpleCopyRepository.findAll(copyIdList);
        this.simpleCopyRepository.delete(simpleCopies);
    }

    @Override
    public void updateCopy(SimpleCopy copy) throws IllegalEntityAttributeException {
        try {
            this.simpleCopyRepository.save(copy);
        } catch (Exception e) {
            throw new IllegalEntityAttributeException();
        }
    }

    @Override
    public void updateCopies(List<SimpleCopy> copyList) throws IllegalEntityAttributeException {
        try {
            this.simpleCopyRepository.save(copyList);
        } catch (Exception e) {
            throw new IllegalEntityAttributeException();
        }

    }
}
