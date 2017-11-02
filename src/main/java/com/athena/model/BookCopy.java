package com.athena.model;

import javax.persistence.*;

/**
 * Created by Tommy on 2017/8/29.
 */
@Entity
@Table(name = "book_copy")
@PrimaryKeyJoinColumn(name = "copy_id", referencedColumnName = "id")
public class BookCopy extends SimpleCopy {
    private Book book;

    public BookCopy() {
        super();
        this.book = null;
    }

    public BookCopy(Long id) {
        this();
        this.id = id;
    }

    public BookCopy(CopyInfo copyInfo, Book book) {
        super(copyInfo);
        this.book = book;
    }

    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BookCopy bookCopy = (BookCopy) o;

        return book != null ? book.equals(bookCopy.book) : bookCopy.book == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }
}
