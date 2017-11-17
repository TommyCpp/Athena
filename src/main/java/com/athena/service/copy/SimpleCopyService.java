package com.athena.service.copy;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.model.CopyStatus;
import com.athena.model.SimpleCopy;
import com.athena.repository.jpa.copy.SimpleCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Tommy on 2017/8/24.
 */
@Service
public class SimpleCopyService implements GenericCopyService<SimpleCopy> {
    private final SimpleCopyRepository simpleCopyRepository;

    @Autowired
    public SimpleCopyService(SimpleCopyRepository simpleCopyRepository) {
        this.simpleCopyRepository = simpleCopyRepository;
    }


    @Override
    public SimpleCopy add(SimpleCopy copy) {
        return this.simpleCopyRepository.save(copy);
    }

    @Override
    public List<SimpleCopy> add(Iterable<SimpleCopy> copies) {
        return this.simpleCopyRepository.save(copies);
    }

    @Override
    public SimpleCopy get(Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        SimpleCopy copy = this.simpleCopyRepository.findOne(id);
        if (copy == null) {
            throw new IdOfResourceNotFoundException();
        }
        return copy;
    }

    @Override
    public List<SimpleCopy> get(Iterable<Long> idList) {
        return this.simpleCopyRepository.findAll(idList);
    }


    @Override
    public void deleteById(Long id) {
        this.simpleCopyRepository.delete(id);
    }

    @Override
    public void deleteById(List<Long> copyIdList) {
        List<SimpleCopy> simpleCopies = this.simpleCopyRepository.findAll(copyIdList);
        this.simpleCopyRepository.delete(simpleCopies);
    }

    @Override
    public SimpleCopy update(SimpleCopy copy) throws IllegalEntityAttributeException {
        try {
            return this.simpleCopyRepository.save(copy);
        } catch (Exception e) {
            throw new IllegalEntityAttributeException();
        }
    }

    @Override
    public List<SimpleCopy> update(Iterable<SimpleCopy> copyList) throws IllegalEntityAttributeException {
        try {
            return this.simpleCopyRepository.save(copyList);
        } catch (Exception e) {
            throw new IllegalEntityAttributeException();
        }

    }

    public SimpleCopy verifyReturnedCopy(SimpleCopy simpleCopy, boolean isDamaged) {
        if (isDamaged) {
            simpleCopy.setStatus(CopyStatus.DAMAGED);
            //todo: DamageHandler
        } else {
            simpleCopy.setStatus(CopyStatus.AVAILABLE);
        }
        return this.simpleCopyRepository.save(simpleCopy);
    }
}
