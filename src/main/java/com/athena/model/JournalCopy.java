package com.athena.model;

import javax.persistence.*;

/**
 * Created by Tommy on 2017/8/30.
 */
@Entity
@Table(name = "copy")
public class JournalCopy extends Copy {
    private Journal journal;

    @ManyToOne
    @JoinTable(name = "journal_copy",
            joinColumns = @JoinColumn(name = "copy_id", table = "copy", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "issn", table = "journal", referencedColumnName = "issn"),
                    @JoinColumn(name = "year", table = "journal", referencedColumnName = "year"),
                    @JoinColumn(name = "index", table = "journal", referencedColumnName = "index")
            }
    )
    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

}
