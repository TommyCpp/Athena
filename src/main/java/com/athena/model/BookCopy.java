package com.athena.model;

import javax.persistence.*;

/**
 * Created by Tommy on 2017/8/29.
 */
@Entity
@Table(name = "copy")
public class BookCopy extends Copy {
    private Book book;

    @ManyToOne
    @JoinTable(name = "book_copy",
            joinColumns = @JoinColumn(name = "copy_id", table = "copy", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "isbn", table = "book", referencedColumnName = "isbn")
    )
    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
