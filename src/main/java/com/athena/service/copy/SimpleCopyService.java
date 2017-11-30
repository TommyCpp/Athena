package com.athena.service.copy;

import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.CopyStatus;
import com.athena.model.SimpleCopy;
import com.athena.repository.jpa.copy.SimpleCopyRepository;
import com.athena.security.model.Account;
import com.athena.service.borrow.PublicationDamagedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Tommy on 2017/8/24.
 */
@Service
public class SimpleCopyService implements GenericCopyService<SimpleCopy> {
    private final SimpleCopyRepository simpleCopyRepository;
    private PublicationDamagedHandler publicationDamagedHandler;

    @Autowired
    public SimpleCopyService(SimpleCopyRepository simpleCopyRepository, PublicationDamagedHandler publicationDamagedHandler) {
        this.simpleCopyRepository = simpleCopyRepository;
        this.publicationDamagedHandler = publicationDamagedHandler;
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
    public SimpleCopy get(Long id) throws ResourceNotFoundByIdException, InvalidCopyTypeException {
        SimpleCopy copy = this.simpleCopyRepository.findOne(id);
        if (copy == null) {
            throw new ResourceNotFoundByIdException();
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

    public SimpleCopy verifyReturnedCopy(Account account, SimpleCopy simpleCopy, boolean isDamaged) {
        if (isDamaged) {
            simpleCopy.setStatus(CopyStatus.DAMAGED);
            publicationDamagedHandler.handleDamage(account,simpleCopy);
        } else {
            simpleCopy.setStatus(CopyStatus.AVAILABLE);
        }
        return this.simpleCopyRepository.save(simpleCopy);
    }
}
