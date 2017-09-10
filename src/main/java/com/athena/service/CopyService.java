package com.athena.service;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.IllegalEntityAttributeExcpetion;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.exception.MixedCopyTypeException;
import com.athena.model.BookCopy;
import com.athena.model.Copy;
import com.athena.model.JournalCopy;
import com.athena.model.SimpleCopy;
import com.athena.repository.jpa.BookCopyRepository;
import com.athena.repository.jpa.BookRepository;
import com.athena.repository.jpa.JournalCopyRepository;
import com.athena.repository.jpa.SimpleCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Tommy on 2017/8/24.
 */
@Service
public class CopyService {
    protected final SimpleCopyRepository simpleCopyRepository;
    protected final BookRepository bookRepository;
    protected final BookCopyRepository bookCopyRepository;
    protected final JournalCopyRepository journalCopyRepository;

    @Autowired
    public CopyService(SimpleCopyRepository simpleCopyRepository, BookRepository bookRepository, BookCopyRepository bookCopyRepository, JournalCopyRepository journalCopyRepository) {
        this.simpleCopyRepository = simpleCopyRepository;
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.journalCopyRepository = journalCopyRepository;
    }


    public void saveCopies(List<? extends Copy> copyList) {
        if (copyList == null || copyList.size() == 0) {
        } else {
            //get the instance of the element
            if (copyList.get(0) instanceof BookCopy) {
                this.bookCopyRepository.save((List<BookCopy>) copyList);
            }
            if (copyList.get(0) instanceof JournalCopy) {
                this.journalCopyRepository.save((List<JournalCopy>) copyList);
            }
        }
    }


    public void removeCopies(List<? extends Copy> copyList) {
        if (copyList != null && copyList.size() != 0) {
            if (copyList.get(0) instanceof BookCopy) {
                this.bookCopyRepository.delete((List<BookCopy>) copyList);
            }
            if (copyList.get(0) instanceof JournalCopy) {
                this.journalCopyRepository.delete((List<JournalCopy>) copyList);
            }
        }
    }

    public Copy getCopy(Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        SimpleCopy copy = this.simpleCopyRepository.findOne(id);
        if (copy == null) {
            throw new IdOfResourceNotFoundException();
        }
        return copy;
    }

    public Copy getCopy(Long id, String type) throws InvalidCopyTypeException, IdOfResourceNotFoundException {
        Copy copy = null;
        switch (type) {
            case "Book": {
                copy = this.bookCopyRepository.findOne(id);
            }
            break;
            case "Journal": {
                copy = this.journalCopyRepository.findOne(id);
            }
            break;
            default: {
                throw new InvalidCopyTypeException();
            }
        }

        //if the id is not exist
        if (copy == null) {
            throw new IdOfResourceNotFoundException();
        }
        return copy;
    }

    public List<? extends Copy> getCopies(List<Long> idList) {
        return this.simpleCopyRepository.findAll(idList);
    }

    public void deleteCopy(Long id) {
        this.simpleCopyRepository.delete(id);
    }

    public void deleteCopies(List<Long> ids) throws MixedCopyTypeException {
        List<SimpleCopy> copies = this.simpleCopyRepository.findAll(ids);
        if (copies.size() == 0) {
            throw new EmptyResultDataAccessException(ids.size());
        }
        this.simpleCopyRepository.delete(copies);
    }

    public void updateCopies(List<? extends Copy> copyList) throws IllegalEntityAttributeExcpetion, MixedCopyTypeException {
        try {
            if (copyList.stream().anyMatch(o -> !(o instanceof SimpleCopy))) {
                throw new MixedCopyTypeException(SimpleCopy.class);
            }

            this.simpleCopyRepository.save((List<SimpleCopy>) copyList);
        } catch (IllegalArgumentException e) {
            throw new IllegalEntityAttributeExcpetion();
        }
    }

    public void updateCopy(Copy copy) {
        if (copy instanceof SimpleCopy) {
            this.simpleCopyRepository.save((SimpleCopy) copy);
        }
    }


}
