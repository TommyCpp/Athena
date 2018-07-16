package com.athena.model.security;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Tommy on 2018/1/24.
 */
@Entity
@Table(name = "block_record", schema = "Athena")
public class BlockRecord {
    private String id;
    private Boolean enabled;
    private Timestamp createdAt;
    private String note;
    private User blockHandler;
    private User blockedUser;
    private User unblockHandler;

    public BlockRecord() {
        this.id = UUID.randomUUID().toString();
        this.enabled = true;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public BlockRecord(User blockHandler, User blockedUser) {
        super();
        this.blockHandler = blockHandler;
        this.blockedUser = blockedUser;
    }

    public BlockRecord(String note, User blockHandler, User blockedUser) {
        this.note = note;
        this.blockHandler = blockHandler;
        this.blockedUser = blockedUser;
    }

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "enabled", nullable = true)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "note", nullable = true, length = -1)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlockRecord that = (BlockRecord) o;

        return id != null ? !id.equals(that.id) : that.id != null;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @ManyToOne
    @JoinColumn(name = "block_handler_id", referencedColumnName = "id", nullable = false)
    public User getBlockHandler() {
        return blockHandler;
    }

    public void setBlockHandler(User handlerId) {
        this.blockHandler = handlerId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getBlockedUser() {
        return blockedUser;
    }

    public void setBlockedUser(User userId) {
        this.blockedUser = userId;
    }

    @ManyToOne
    @JoinColumn(name = "unblock_handler_id", referencedColumnName = "id")
    public User getUnblockHandler() {
        return unblockHandler;
    }

    public void setUnblockHandler(User unblockHandler) {
        this.unblockHandler = unblockHandler;
    }
}
