package com.athena.model.copy;

import com.athena.model.publication.Journal;
import com.athena.model.publication.Publication;

import javax.persistence.*;

/**
 * Created by Tommy on 2017/8/30.
 */
@Entity
@Table(name = "journal_copy")
@PrimaryKeyJoinColumn(name = "copy_id", referencedColumnName = "id")
public class JournalCopy extends SimpleCopy implements PublicationCopy{
    private Journal journal;

    public JournalCopy() {
        super();
        this.journal = null;
    }

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "year", table = "journal_copy", referencedColumnName = "year"),
            @JoinColumn(name = "issn", table = "journal_copy", referencedColumnName = "issn"),
            @JoinColumn(name = "issue", table = "journal_copy", referencedColumnName = "issue")
    })
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

    @Transient
    @Override
    public Publication getPublication() {
        return this.getJournal();
    }
}
