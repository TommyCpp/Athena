package com.athena.service.publication;

import com.athena.exception.http.ResourceNotDeletable;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.model.copy.JournalCopy;
import com.athena.model.publication.Journal;
import com.athena.model.publication.JournalPK;
import com.athena.repository.jpa.JournalRepository;
import com.athena.repository.jpa.copy.JournalCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Tommy on 2017/10/23.
 */
@Service
public class JournalService implements PublicationService<Journal, JournalPK> {


    private final JournalRepository journalRepository;
    private final JournalCopyRepository journalCopyRepository;

    @Autowired
    public JournalService(JournalRepository journalRepository, JournalCopyRepository journalCopyRepository) {
        this.journalRepository = journalRepository;
        this.journalCopyRepository = journalCopyRepository;
    }


    @Override
    public Journal get(JournalPK journalPK) throws ResourceNotFoundByIdException {
        Journal journal = this.journalRepository.findOne(journalPK);
        if (Objects.isNull(journal)) {
            throw new ResourceNotFoundByIdException();
        }
        return journal;
    }

    @Override
    public List<Journal> get(Iterable<JournalPK> journalPks) {
        return this.journalRepository.findAll(journalPks);
    }

    @Override
    public Journal update(Journal journal) throws ResourceNotFoundByIdException {
        if (this.journalRepository.exists(journal.getId())) {
            return this.journalRepository.save(journal);
        } else {
            throw new ResourceNotFoundByIdException();
        }
    }

    @Override
    public void delete(Journal journal) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        Objects.requireNonNull(journal);
        if (!this.journalRepository.exists(journal.getId())) {
            //if the journal is not exits
            throw new ResourceNotFoundByIdException();
        }
        List<JournalCopy> journalCopies = this.journalCopyRepository.isNotDeletable(journal.getId());
        if (journalCopies.size() > 0) {
            throw new ResourceNotDeletable(journalCopies);
        }
        //will delete the copy cascade.
        this.journalRepository.delete(journal);
    }

    @Override
    public void delete(Iterable<Journal> journals) throws ResourceNotFoundByIdException, ResourceNotDeletable {
        Objects.requireNonNull(journals);
        List<Journal> notDeletableJournals = StreamSupport.stream(journals.spliterator(), false).filter(journal -> !this.journalCopyRepository.isNotDeletable(journal.getId()).isEmpty()).collect(Collectors.toList());
        if (notDeletableJournals.size() > 0) {
            throw new ResourceNotDeletable(notDeletableJournals);
        }
        this.journalRepository.delete(journals);
    }

    @Override
    public Journal add(Journal journal) {
        Objects.requireNonNull(journal);
        return this.journalRepository.save(journal);
    }

    @Override
    public List<Journal> add(Iterable<Journal> journals) {
        Objects.requireNonNull(journals);
        return this.journalRepository.save(journals);
    }

    @Override
    public Page<Journal> search(Specification specification, Pageable pageable) {
        return this.journalRepository.findAll(specification, pageable);
    }
}
