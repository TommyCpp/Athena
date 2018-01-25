package com.athena.model.copy;

import com.athena.model.borrow.Borrow;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Tommy on 2017/11/30.
 */
@Document(collection = "copyDamageReport")
public class CopyDamageReport {
    @Id
    private String id;

    @Field
    private Long copyId;

    @Field
    private Long handlerId;

    @Field
    private Borrow lastKnownBorrow;

    @Field
    private String description;

    @Field
    private String imageId;

    @Field
    private Date createdAt;


    public Long getCopyId() {
        return copyId;
    }

    public void setCopyId(Long copyId) {
        this.copyId = copyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId(UUID id){
        this.id = id.toString();
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public Borrow getLastKnownBorrow() {
        return lastKnownBorrow;
    }

    public void setLastKnownBorrow(Borrow lastKnownBorrow) {
        this.lastKnownBorrow = lastKnownBorrow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CopyDamageReport that = (CopyDamageReport) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (copyId != null ? !copyId.equals(that.copyId) : that.copyId != null) return false;
        if (handlerId != null ? !handlerId.equals(that.handlerId) : that.handlerId != null) return false;
        if (lastKnownBorrow != null ? !lastKnownBorrow.equals(that.lastKnownBorrow) : that.lastKnownBorrow != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (imageId != null ? !imageId.equals(that.imageId) : that.imageId != null) return false;
        return createdAt != null ? createdAt.equals(that.createdAt) : that.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (copyId != null ? copyId.hashCode() : 0);
        result = 31 * result + (handlerId != null ? handlerId.hashCode() : 0);
        result = 31 * result + (lastKnownBorrow != null ? lastKnownBorrow.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imageId != null ? imageId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
