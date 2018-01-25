package com.athena.model.copy;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by Tommy on 2017/8/31.
 * <p>
 * Contain the basic info of an generic copy, can be used as the Jackson target class of input http message that create an copy.</p>
 */
@MappedSuperclass
public class CopyVo {
    /**
     * 0: new included;
     * 1: available;
     * 2: booked;
     * 3: checked out;
     * 4: reserved;
     * 5: damaged
     * */
    protected Integer status;

    public CopyVo() {
        this.status = CopyStatus.CREATED;
    }

    public CopyVo(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "status", nullable = false,table = "copy")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
