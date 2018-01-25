package com.athena.model.copy;

import com.athena.model.borrow.Borrow;

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

    public SimpleCopy(CopyVo copyVo) {
        super(copyVo);
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
