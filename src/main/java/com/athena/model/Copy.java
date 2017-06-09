package com.athena.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Tommy on 2017/6/9.
 */
@Entity
public class Copy {
    private CopyPK id;
    private Integer status;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Book book;

    @EmbeddedId
    public CopyPK getId() {
        return id;
    }

    public void setId(CopyPK id) {
        this.id = id;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "created_date", nullable = true)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "updated_date", nullable = true)
    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Copy copy = (Copy) o;

        if (id.equals(id)) return false;
        if (status != null ? !status.equals(copy.status) : copy.status != null) return false;
        if (createdDate != null ? !createdDate.equals(copy.createdDate) : copy.createdDate != null) return false;
        if (updatedDate != null ? !updatedDate.equals(copy.updatedDate) : copy.updatedDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false)
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


}
