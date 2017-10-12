package com.athena.service.copy;

import com.athena.exception.*;
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
public class BookCopyService implements CopyService<BookCopy, Long, Long> {


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
    public BookCopy getCopy(Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        BookCopy bookCopy = this.bookCopyRepository.findByIdAndBookIsNotNull(id);
        if (bookCopy == null) {
            throw new IdOfResourceNotFoundException();
        }
        return bookCopy;
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
    public void deleteCopies(@NotNull List<Long> copyIdList) throws MixedCopyTypeException {
        List<BookCopy> bookCopyList = this.getCopies(copyIdList);
        if (bookCopyList.size() != copyIdList.size()) {
            throw new MixedCopyTypeException(BookCopy.class);
        }
        this.bookCopyRepository.delete(bookCopyList);
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


    /**
     * Update
     */

    @Override
    public void updateCopy(BookCopy copy) {
        this.bookCopyRepository.update(copy);
    }

    @Override
    public void updateCopies(List<BookCopy> copyList) throws IllegalEntityAttributeException {
        try {
            this.bookCopyRepository.update(copyList);
        } catch (Exception e) {
            throw new IllegalEntityAttributeException();
        }
    }

    @Override
    public void addCopy(BookCopy copy) {
        this.bookCopyRepository.save(copy);
    }

    public void addCopies(List<BookCopy> bookCopyList) {
        this.bookCopyRepository.save(bookCopyList);
    }
}
