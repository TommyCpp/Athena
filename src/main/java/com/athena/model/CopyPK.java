package com.athena.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Tommy on 2017/6/9.
 */
@Embeddable
public class CopyPK implements Serializable {
    private long book;
    private int copyId;

    public CopyPK() {
    }

    public CopyPK(long book, int copyId) {
        this.book = book;
        this.copyId = copyId;
    }

    @Column(name = "isbn", nullable = false)
    @Id
    public long getBook() {
        return book;
    }

    public void setBook(long book) {
        this.book = book;
    }

    @Column(name = "copy_id", nullable = false)
    @Id
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
        int result = (int) (book ^ (book >>> 32));
        result = 31 * result + copyId;
        return result;
    }
}
