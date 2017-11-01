package com.athena.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Tommy on 2017/8/30.
 */
@Entity
@Table(name = "copy")
public class JournalCopy extends SimpleCopy {
    private Journal journal;

    public JournalCopy() {
        super();
        this.journal = null;
    }

    public JournalCopy(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinTable(name = "journal_copy",
            joinColumns = @JoinColumn(name = "copy_id", table = "copy", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = {@JoinColumn(name = "issn", table = "journal", referencedColumnName = "issn", nullable = false),
                    @JoinColumn(name = "year", table = "journal", referencedColumnName = "year", nullable = false),
                    @JoinColumn(name = "issue", table = "journal", referencedColumnName = "issue", nullable = false)}
    )
    @NotNull
    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        JournalCopy that = (JournalCopy) o;

        return journal != null ? journal.equals(that.journal) : that.journal == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (journal != null ? journal.hashCode() : 0);
        return result;
    }
}
