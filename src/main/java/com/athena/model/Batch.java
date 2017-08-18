package com.athena.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by Tommy on 2017/8/16.
 */
@Document(collection = "batch")
public class Batch {
    @Id
    private String id;
    @Field
    private String type;
    @Field
    private Date createAt;
    @Field
    private List<String> content;

    @PersistenceConstructor
    public Batch(String id, String type, Date createAt, List<String> content) {
        this.id = id;
        this.type = type;
        this.createAt = createAt;
        this.content = content;
    }

    public String getId() {
        return id;
    }


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

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

        Batch batch = (Batch) o;

        if (id != null ? !id.equals(batch.id) : batch.id != null) return false;
        if (type != null ? !type.equals(batch.type) : batch.type != null) return false;
        if (createAt != null ? !createAt.equals(batch.createAt) : batch.createAt != null) return false;
        return content != null ? content.equals(batch.content) : batch.content == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", createAt=" + createAt +
                ", content=" + content +
                '}';
    }
}
