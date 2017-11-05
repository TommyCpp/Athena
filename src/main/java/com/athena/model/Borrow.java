package com.athena.model;

import com.athena.model.listener.BorrowListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Tommy on 2017/10/31.
 */
@Entity
@Table(name = "borrow")
@EntityListeners(BorrowListener.class)
public class Borrow {
    private String id;
    private Boolean enable;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private String type;
    private User user;
    private SimpleCopy copy;

    public Borrow() {
        this.id = UUID.randomUUID().toString();
        this.createdDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    @Id
    @Column(name = "id", nullable = false, length = 128)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "enable", nullable = false)
    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
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


    @Basic
    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Borrow borrow = (Borrow) o;

        if (id != null ? !id.equals(borrow.id) : borrow.id != null) return false;
        if (enable != null ? !enable.equals(borrow.enable) : borrow.enable != null) return false;
        if (createdDate != null ? !createdDate.equals(borrow.createdDate) : borrow.createdDate != null) return false;
        if (updatedDate != null ? !updatedDate.equals(borrow.updatedDate) : borrow.updatedDate != null) return false;
        if (type != null ? !type.equals(borrow.type) : borrow.type != null) return false;
        if (user != null ? !user.equals(borrow.user) : borrow.user != null) return false;
        return copy != null ? copy.equals(borrow.copy) : borrow.copy == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (enable != null ? enable.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (copy != null ? copy.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "copy_id", referencedColumnName = "id")
    public SimpleCopy getCopy() {
        return copy;
    }

    public void setCopy(SimpleCopy copy) {
        this.copy = copy;
    }

}
