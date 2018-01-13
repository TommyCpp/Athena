package com.athena.model.domain.copy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Tommy on 2017/6/9.
 */
@MappedSuperclass
abstract public class AbstractCopy extends CopyVO {
    protected Long id;
    protected Timestamp createdDate;
    protected Timestamp updatedDate;

    public AbstractCopy() {
        super();
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
        this.createdDate = timestamp;
        this.updatedDate = timestamp;
    }

    public AbstractCopy(CopyVO copyVO) {
        super(copyVO.status);
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
        this.createdDate = timestamp;
        this.updatedDate = timestamp;
    }


    @Id
    @GenericGenerator(name = "copy_id_generator", strategy = "increment")
    @GeneratedValue(generator = "copy_id_generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Basic
    @Column(name = "created_date", nullable = true, table = "copy")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "updated_date", nullable = true, table = "copy")
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

        AbstractCopy abstractCopy = (AbstractCopy) o;

        if (id.equals(abstractCopy.id)) return false;
        if (status != null ? !status.equals(abstractCopy.status) : abstractCopy.status != null) return false;
        if (createdDate != null ? !createdDate.equals(abstractCopy.createdDate) : abstractCopy.createdDate != null) return false;
        if (updatedDate != null ? !updatedDate.equals(abstractCopy.updatedDate) : abstractCopy.updatedDate != null) return false;

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


}
