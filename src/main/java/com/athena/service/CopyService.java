package com.athena.service;

import com.athena.exception.BookNotFoundException;
import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.model.Book;
import com.athena.model.BookCopy;
import com.athena.model.Copy;
import com.athena.model.JournalCopy;
import com.athena.repository.jpa.BookCopyRepository;
import com.athena.repository.jpa.BookRepository;
import com.athena.repository.jpa.JournalCopyRepository;
import com.athena.repository.jpa.SimpleCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2017/8/24.
 */
@Service
public class CopyService {
    private final SimpleCopyRepository simpleCopyRepository;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final JournalCopyRepository journalCopyRepository;

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
            if(copyList.get(0) instanceof JournalCopy){
                this.journalCopyRepository.delete((List<JournalCopy>) copyList);
            }
        }
    }

    public Copy getCopy(Long id) throws IdOfResourceNotFoundException {
        //todo: how to get different kind of copy?
        return new Copy();
    }

    public List<Copy> getCopies(Long isbn) throws BookNotFoundException {
        Book book = this.bookRepository.findOne(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }

        return new ArrayList<Copy>();
    }
}
