package com.athena.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Tommy on 2017/9/2.
 * <p>
 * Delegation of Copy since that Copy has been annotated by @MappedSuperClass
 */
@Entity
@Table(name = "copy")
public class SimpleCopy extends Copy {
    private List<Borrow> borrows;

    @OneToMany
    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }
}
