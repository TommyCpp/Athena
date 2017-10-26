package com.athena.service;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.exception.http.ResourceNotDeletable;
import com.athena.model.Journal;
import com.athena.model.JournalCopy;
import com.athena.model.JournalPK;
import com.athena.repository.jpa.JournalRepository;
import com.athena.repository.jpa.copy.JournalCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Tommy on 2017/10/23.
 */
@Service
public class JournalService implements PublicationService<Journal,JournalPK>{


    private final JournalRepository journalRepository;
    private final JournalCopyRepository journalCopyRepository;

    @Autowired
    public JournalService(JournalRepository journalRepository, JournalCopyRepository journalCopyRepository) {
        this.journalRepository = journalRepository;
        this.journalCopyRepository = journalCopyRepository;
    }


    @Override
    public Journal get(JournalPK journalPK) throws IdOfResourceNotFoundException {
        Journal journal = this.journalRepository.findOne(journalPK);
        if (Objects.isNull(journal)) {
            throw new IdOfResourceNotFoundException();
        }
        return journal;
    }

    @Override
    public List<Journal> get(Iterable<JournalPK> journalPks) {
        return this.journalRepository.findAll(journalPks);
    }

    @Override
    public Journal update(Journal journal) throws IdOfResourceNotFoundException {
        if (this.journalRepository.exists(journal.getId())) {
            return this.journalRepository.save(journal);
        }
        else{
            throw new IdOfResourceNotFoundException();
        }
    }

    @Override
    public void delete(Journal journal) throws IdOfResourceNotFoundException, ResourceNotDeletable {
        Objects.requireNonNull(journal);
        if (!this.journalRepository.exists(journal.getId())) {
           //if the journal is not exits
            throw new IdOfResourceNotFoundException();
        }
        List<JournalCopy> journalCopies = this.journalCopyRepository.isNotDeletable(journal.getId());
        if(journalCopies.size() > 0){
            throw new ResourceNotDeletable(journalCopies);
        }
        //will delete the copy cascade.
        this.journalRepository.delete(journal);
    }

    @Override
    public void delete(Iterable<Journal> journals) throws IdOfResourceNotFoundException, ResourceNotDeletable {
        Objects.requireNonNull(journals);
        List<Journal> notDeletableJournals = StreamSupport.stream(journals.spliterator(), false).filter(journal -> !this.journalCopyRepository.isNotDeletable(journal.getId()).isEmpty()).collect(Collectors.toList());
        if(notDeletableJournals.size() > 0){
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
}
