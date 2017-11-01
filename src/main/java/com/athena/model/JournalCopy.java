package com.athena.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Tommy on 2017/8/30.
 */
@Entity
@Table(name = "journal_copy")
@SecondaryTable(name = "copy", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "copy_id"))
@AttributeOverride(name = "id", column = @Column(name = "copy_id", table = "journal_copy"))
public class JournalCopy extends Copy {
    private Journal journal;

    public JournalCopy() {
        super();
        this.journal = null;
    }

    public JournalCopy(Long id) {
        this.id = id;
    }

    @Override
    @Id
    @GenericGenerator(name = "copy_id_generator", strategy = "increment")
    @GeneratedValue(generator = "copy_id_generator")
    public Long getId() {
        return super.getId();
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
}
