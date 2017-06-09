package com.athena.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Tommy on 2017/6/9.
 */
@Embeddable
public class CopyPK implements Serializable {
    private Book book;
    private int copyId;

    public CopyPK() {
    }

    public CopyPK(Book book, int copyId) {
        this.book = book;
        this.copyId = copyId;
    }

    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false)
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Column(name = "copy_id", nullable = false)
    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CopyPK copyPK = (CopyPK) o;

        if (book != copyPK.book) return false;
        if (copyId != copyPK.copyId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = book != null ? book.hashCode() : 0;
        result = 31 * result + copyId;
        return result;
    }
}
