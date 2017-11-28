package com.athena.model;

import com.athena.util.EntityUtil;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Tommy on 2017/11/23.
 */
@Entity
@Table(name = "block")
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class BlockedUser extends User {
    private Timestamp createdAt;
    private User handler;

    public BlockedUser() {
        super();
    }

    public BlockedUser(User user) {
        EntityUtil.copyFromParent(this, user);
    }

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
        if (!super.equals(o)) return false;

        BlockedUser that = (BlockedUser) o;

        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        return handler != null ? handler.equals(that.handler) : that.handler == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (handler != null ? handler.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "handler_id", referencedColumnName = "id", nullable = false)
    public User getHandler() {
        return handler;
    }

    public void setHandler(User handler) {
        this.handler = handler;
    }
}
