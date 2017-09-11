package com.athena.service;

import com.athena.exception.*;
import com.athena.model.Book;
import com.athena.model.BookCopy;
import com.athena.model.Copy;
import com.athena.repository.jpa.BookCopyRepository;
import com.athena.repository.jpa.BookRepository;
import com.athena.repository.jpa.JournalCopyRepository;
import com.athena.repository.jpa.SimpleCopyRepository;
import com.athena.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Tommy on 2017/9/2.
 */
@Service
public class BookCopyService extends CopyService {


    /**
     * Instantiates a new Book copy service.
     *
     * @param simpleCopyRepository  the simple copy repository
     * @param bookRepository        the book repository
     * @param bookCopyRepository    the book copy repository
     * @param journalCopyRepository the journal copy repository
     */
    @Autowired
    public BookCopyService(SimpleCopyRepository simpleCopyRepository, BookRepository bookRepository, BookCopyRepository bookCopyRepository, JournalCopyRepository journalCopyRepository) {
        super(simpleCopyRepository, bookRepository, bookCopyRepository, journalCopyRepository);
    }

    @Override
    public BookCopy getCopy(Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        return (BookCopy) super.getCopy(id, "Book");
    }

    /**
     * Gets copies.
     *
     * @param isbn the isbn
     * @return the copies
     * @throws IdOfResourceNotFoundException the id of resource not found exception
     */
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

    /**
     * Delete copy.
     *
     * @param isbn the isbn
     * @param id   the id
     * @throws BookNotFoundException          the book not found exception
     * @throws IsbnAndCopyIdMismatchException the isbn and copy id mismatch exception
     */
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


    /**
     * Delete copies.
     *
     * @param isbn the isbn
     * @throws IdOfResourceNotFoundException the id of resource not found exception
     */
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

    @Override
    public void updateCopies(List<? extends Copy> copyList) throws IllegalEntityAttributeExcpetion, MixedCopyTypeException {
        if (ListUtil.genericTypeIs(copyList, BookCopy.class)) {
            throw new MixedCopyTypeException(BookCopy.class);
        }
        try {
            this.bookCopyRepository.save((List<BookCopy>) copyList);
        } catch (IllegalArgumentException e) {
            throw new IllegalEntityAttributeExcpetion();
        }
    }
}
