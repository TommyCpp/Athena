package com.athena.service;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.IllegalEntityAttributeExcpetion;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.model.SimpleCopy;
import com.athena.repository.jpa.BookCopyRepository;
import com.athena.repository.jpa.BookRepository;
import com.athena.repository.jpa.JournalCopyRepository;
import com.athena.repository.jpa.SimpleCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Tommy on 2017/8/24.
 */
@Service
public class SimpleCopyService implements GenericCopyService<SimpleCopy, Long> {
    private final SimpleCopyRepository simpleCopyRepository;
    private final BookCopyRepository bookCopyRepository;
    private final JournalCopyRepository journalCopyRepository;
    private final BookRepository bookRepository;

    @Autowired
    public SimpleCopyService(SimpleCopyRepository simpleCopyRepository, BookRepository bookRepository, BookCopyRepository bookCopyRepository, JournalCopyRepository journalCopyRepository) {
        this.simpleCopyRepository = simpleCopyRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.journalCopyRepository = journalCopyRepository;
        this.bookRepository = bookRepository;
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
    public void updateCopy(SimpleCopy copy) throws IllegalEntityAttributeExcpetion {
        try {
            this.simpleCopyRepository.save(copy);
        } catch (Exception e) {
            throw new IllegalEntityAttributeExcpetion();
        }
    }

    @Override
    public void updateCopies(List<SimpleCopy> copyList) throws IllegalEntityAttributeExcpetion {
        try {
            this.simpleCopyRepository.save(copyList);
        } catch (Exception e) {
            throw new IllegalEntityAttributeExcpetion();
        }

    }
}
