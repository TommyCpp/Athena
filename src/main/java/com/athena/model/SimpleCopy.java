package com.athena.model;

import com.athena.model.domain.copy.AbstractCopy;
import com.athena.model.domain.copy.CopyVO;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Tommy on 2017/9/2.
 *
 */
@Entity
@Table(name = "copy")
@Inheritance(strategy = InheritanceType.JOINED)
public class SimpleCopy extends AbstractCopy {
    public SimpleCopy() {
    }

    public SimpleCopy(CopyVO copyVO) {
        super(copyVO);
    }

    private List<Borrow> borrows;

    @OneToMany
    @JoinColumn(name = "copy_id", referencedColumnName = "id")
    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }
}
