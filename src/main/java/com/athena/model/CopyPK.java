package com.athena.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Tommy on 2017/6/9.
 */
@Embeddable
public class CopyPK implements Serializable {
    private long isbn;
    private int copyId;

    public CopyPK() {
    }

    public CopyPK(long isbn, int copyId) {
        this.isbn = isbn;
        this.copyId = copyId;
    }

    @Column(name="isbn",nullable = false)
    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
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

        if (isbn != copyPK.isbn) return false;
        if (copyId != copyPK.copyId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) isbn;
        result = 31 * result + copyId;
        return result;
    }
}
