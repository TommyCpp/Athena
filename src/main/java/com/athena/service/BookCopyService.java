package com.athena.service;

import com.athena.exception.*;
import com.athena.model.Book;
import com.athena.model.BookCopy;
import com.athena.model.Copy;
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

    @Transactional
    public void deleteCopy(Long isbn, Long id) throws BookNotFoundException, IsbnAndCopyIdMismatchException {
        //Check if the book has id
        Book book = this.bookRepository.findOne(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }
        if (this.bookCopyRepository.findByIdAndBookIsNotNull(id) != null) {
            this.deleteCopy(id);
        } else {
            throw new IsbnAndCopyIdMismatchException(isbn, id);
        }

    }


    @Override
    public void deleteCopy(Long id) {
        this.bookCopyRepository.delete(id);
    }

    @Override
    public void deleteCopies(List<Long> ids) throws MixedCopyTypeException {
        List<BookCopy> copies = this.getCopies(ids);
        if (ids.size() != copies.size()) {
            //indicate that some of the id do not belongs to BookCopy
            throw new MixedCopyTypeException(BookCopy.class);
        }
        if (copies.size() == 0) {
            throw new EmptyResultDataAccessException(ids.size());
        }
        this.bookCopyRepository.delete(copies);
    }

    @Transactional
    public void deleteCopies(Long isbn) throws IdOfResourceNotFoundException {
        Book book = this.bookRepository.findOne(isbn);
        if (book == null) {
            throw new IdOfResourceNotFoundException();
        }

        List<BookCopy> copies = this.bookCopyRepository.findByBook(book);

        this.bookCopyRepository.delete(copies);
    }


    @Override
    public void updateCopy(Copy copy) {
        if (copy instanceof BookCopy) {
            this.bookCopyRepository.save((BookCopy) copy);
        }
    }
}
