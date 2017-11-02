package com.athena.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Created by Tommy on 2017/9/2.
 *
 * Delegation of Copy since that Copy has been annotated by @MappedSuperClass
 */
@Entity
@Table(name = "copy")
@Inheritance(strategy = InheritanceType.JOINED)
public class SimpleCopy extends Copy {
    public SimpleCopy() {
    }

    public SimpleCopy(CopyInfo copyInfo) {
        super(copyInfo);
    }
}
