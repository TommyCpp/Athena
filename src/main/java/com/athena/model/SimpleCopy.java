package com.athena.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Tommy on 2017/9/2.
 * <p>
 * Delegation of AbstractCopy since that AbstractCopy has been annotated by @MappedSuperClass
 */
@Entity
@Table(name = "copy")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SimpleCopy extends AbstractCopy {
    private List<Borrow> borrows;

    public SimpleCopy() {
    }

    public SimpleCopy(CopyInfo info) {
        super(info);
    }

    @OneToMany
    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }
}
