package com.athena.service;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.model.Copy;
import com.athena.model.JournalCopy;
import com.athena.repository.jpa.BookCopyRepository;
import com.athena.repository.jpa.BookRepository;
import com.athena.repository.jpa.JournalCopyRepository;
import com.athena.repository.jpa.SimpleCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tommy on 2017/9/2.
 */
@Service
public class JournalCopyService extends CopyService {

    @Autowired
    public JournalCopyService(SimpleCopyRepository simpleCopyRepository, BookRepository bookRepository, BookCopyRepository bookCopyRepository, JournalCopyRepository journalCopyRepository) {
        super(simpleCopyRepository, bookRepository, bookCopyRepository, journalCopyRepository);
    }

    @Override
    public JournalCopy getCopy(Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        return (JournalCopy) super.getCopy(id, "Journal");
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
    public void deleteCopies(List<Long> ids) {
        List<JournalCopy> copies = this.getCopies(ids);
        if (copies.size() == 0) {
            throw new EmptyResultDataAccessException(ids.size());
        }
        this.journalCopyRepository.delete(copies);
    }

    @Override
    public void updateCopy(Copy copy) {
        if (copy instanceof JournalCopy) {
            this.journalCopyRepository.save((JournalCopy) copy);
        }
    }
}
