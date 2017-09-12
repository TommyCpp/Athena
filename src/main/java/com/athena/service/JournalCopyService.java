package com.athena.service;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.IllegalEntityAttributeExcpetion;
import com.athena.exception.MixedCopyTypeException;
import com.athena.model.JournalCopy;
import com.athena.repository.jpa.BookCopyRepository;
import com.athena.repository.jpa.BookRepository;
import com.athena.repository.jpa.JournalCopyRepository;
import com.athena.repository.jpa.SimpleCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tommy on 2017/9/2.
 */
@Service
public class JournalCopyService implements CopyService<JournalCopy> {

    private final SimpleCopyRepository simpleCopyRepository;
    private final BookCopyRepository bookCopyRepository;
    private final JournalCopyRepository journalCopyRepository;
    private final BookRepository bookRepository;

    @Autowired
    public JournalCopyService(SimpleCopyRepository simpleCopyRepository, BookRepository bookRepository, BookCopyRepository bookCopyRepository, JournalCopyRepository journalCopyRepository) {
        this.simpleCopyRepository = simpleCopyRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.journalCopyRepository = journalCopyRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void addCopy(JournalCopy copy) {
        this.journalCopyRepository.save(copy);
    }

    @Override
    public void addCopies(List<JournalCopy> copies) {
        this.journalCopyRepository.save(copies);
    }

    @Override
    public JournalCopy getCopy(Long id) throws IdOfResourceNotFoundException {
        JournalCopy copy = this.journalCopyRepository.findByIdAndJournalIsNotNull(id);
        if (copy == null) {
            throw new IdOfResourceNotFoundException();
        }
        return copy;
    }

    @Override
    public List<JournalCopy> getCopies(List<Long> idList) {
        return this.journalCopyRepository.findByIdIsInAndJournalIsNotNull(idList);
    }

    @Override
    public void deleteCopy(Long id) {
        this.journalCopyRepository.delete(id);
    }

    @Override
    public void deleteCopies(List<Long> ids) throws MixedCopyTypeException {
        List<JournalCopy> copies = this.getCopies(ids);
        if (ids.size() != copies.size()) {
            throw new MixedCopyTypeException(JournalCopy.class);
        }
        this.journalCopyRepository.delete(copies);
    }

    @Override
    public void updateCopy(JournalCopy copy) {
        this.journalCopyRepository.save(copy);
    }

    @Override
    public void updateCopies(List<JournalCopy> copyList) throws IllegalEntityAttributeExcpetion {
        try {
            this.journalCopyRepository.save(copyList);
        } catch (Exception e) {
            throw new IllegalEntityAttributeExcpetion();
        }
    }
}
