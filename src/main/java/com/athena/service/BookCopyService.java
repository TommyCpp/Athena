package com.athena.service;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.model.Book;
import com.athena.model.BookCopy;
import com.athena.repository.jpa.BookCopyRepository;
import com.athena.repository.jpa.BookRepository;
import com.athena.repository.jpa.JournalCopyRepository;
import com.athena.repository.jpa.SimpleCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Tommy on 2017/9/2.
 */
@Service
public class BookCopyService extends CopyService {


    @Autowired
    public BookCopyService(SimpleCopyRepository simpleCopyRepository, BookRepository bookRepository, BookCopyRepository bookCopyRepository, JournalCopyRepository journalCopyRepository) {
        super(simpleCopyRepository, bookRepository, bookCopyRepository, journalCopyRepository);
    }

    @Override
    public BookCopy getCopy(Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        return (BookCopy) super.getCopy(id, "Book");
    }

    public List<BookCopy> getCopies(Long isbn) throws IdOfResourceNotFoundException {
        //get Book
        Book book = this.bookRepository.findOne(isbn);
        if (book == null) {
            throw new IdOfResourceNotFoundException();
        }

        //get Copies
        return this.bookCopyRepository.findByBook(book);

    }

    @Override
    public List<BookCopy> getCopies(List<Long> idList) {
        return this.bookCopyRepository.findByIdIsInAndBookIsNotNull(idList);
    }

    @Override
    public void deleteCopy(Long id) {
        this.bookCopyRepository.delete(id);
    }

    @Override
    public void deleteCopies(List<Long> ids) {
        List<BookCopy> copies = this.getCopies(ids);
        if (copies.size() == 0) {
            throw new EmptyResultDataAccessException(ids.size());
        }
        this.bookCopyRepository.delete(copies);
    }

    @Transactional
    public void deleteCopies(Long isbn) throws IdOfResourceNotFoundException {
        Book book = this.bookRepository.findOne(isbn);
        if(book == null){
            throw new IdOfResourceNotFoundException();
        }

        List<BookCopy> copies = this.bookCopyRepository.findByBook(book);

        this.bookCopyRepository.delete(copies);
    }


}
