package com.athena.service.copy;

import com.athena.exception.http.*;
import com.athena.model.Book;
import com.athena.model.BookCopy;
import com.athena.repository.jpa.BookRepository;
import com.athena.repository.jpa.copy.BookCopyRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Tommy on 2017/9/2.
 */
@Service
public class BookCopyService implements CopyService<BookCopy, Long> {


    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;


    /**
     * Instantiates a new Book copy service.
     *
     * @param bookRepository     the book repository
     * @param bookCopyRepository the book copy repository
     */
    @Autowired
    public BookCopyService(BookRepository bookRepository, BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookCopy get(Long id) throws ResourceNotFoundByIdException, InvalidCopyTypeException {
        BookCopy bookCopy = this.bookCopyRepository.findByIdAndBookIsNotNull(id);
        if (bookCopy == null) {
            throw new ResourceNotFoundByIdException();
        }
        return bookCopy;
    }

    /**
     * Gets copies.
     *
     * @param isbn the isbn
     * @return the copies
     * @throws ResourceNotFoundByIdException the id of resource not found exception
     */
    public List<BookCopy> getCopies(Long isbn) throws ResourceNotFoundByIdException {
        //get Book
        Book book = this.bookRepository.findOne(isbn);
        if (book == null) {
            throw new ResourceNotFoundByIdException();
        }

        //get Copies
        return this.bookCopyRepository.findByBook(book);

    }

    @Override
    public List<BookCopy> get(Iterable<Long> idList) {
        return this.bookCopyRepository.findByIdIsInAndBookIsNotNull(idList);
    }

    /**
     * Delete copy with id and belongs to book.
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
        if (this.bookCopyRepository.findByIdAndBook(id, book) != null) {
            this.deleteById(id);
        } else {
            throw new IsbnAndCopyIdMismatchException(isbn, id);
        }

    }


    @Override
    public void deleteById(Long id) {
        this.bookCopyRepository.delete(id);
    }

    @Override
    public void deleteById(@NotNull List<Long> copyIdList) throws MixedCopyTypeException {
        List<BookCopy> bookCopyList = this.get(copyIdList);
        if (bookCopyList.size() != copyIdList.size()) {
            throw new MixedCopyTypeException(BookCopy.class);
        }
        this.bookCopyRepository.delete(bookCopyList);
    }


    /**
     * Delete copies.
     *
     * @param isbn the isbn
     * @throws ResourceNotFoundByIdException the id of resource not found exception
     */
    @Transactional
    public void deleteCopies(Long isbn) throws ResourceNotFoundByIdException {
        Book book = this.bookRepository.findOne(isbn);
        if (book == null) {
            throw new ResourceNotFoundByIdException();
        }

        List<BookCopy> copies = this.bookCopyRepository.findByBook(book);

        this.bookCopyRepository.delete(copies);
    }


    /**
     * Update
     */

    @Override
    @Transactional
    public BookCopy update(BookCopy copy) throws IllegalEntityAttributeException {
        try {
            return this.bookCopyRepository.update(copy);
        } catch (Exception e) {
            throw new IllegalEntityAttributeException();
        }
    }

    @Override
    public List<BookCopy> update(Iterable<BookCopy> copyList) throws IllegalEntityAttributeException {
        try {
            return this.bookCopyRepository.update(copyList);
        } catch (Exception e) {
            throw new IllegalEntityAttributeException();
        }
    }

    @Override
    public BookCopy add(BookCopy copy) {
        return this.bookCopyRepository.save(copy);
    }

    public List<BookCopy> add(Iterable<BookCopy> bookCopyList) {
        return this.bookCopyRepository.save(bookCopyList);
    }
}
