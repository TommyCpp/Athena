package com.athena.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Tommy on 2017/11/23.
 */
@Entity
@Table(name = "block")
public class Block implements Serializable{
    private Timestamp createdAt;
    private User handler;
    private User user;

    @Basic
    @Column(name = "created_at", nullable = true)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block block = (Block) o;

        if (createdAt != null ? !createdAt.equals(block.createdAt) : block.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return createdAt != null ? createdAt.hashCode() : 0;
    }

    @OneToOne
    @JoinColumn(name = "handler_id", referencedColumnName = "id", nullable = false)
    public User getHandler() {
        return handler;
    }

    public void setHandler(User handler) {
        this.handler = handler;
    }

    @Id
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
