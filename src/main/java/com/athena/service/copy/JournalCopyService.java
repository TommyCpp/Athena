package com.athena.service.copy;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.IllegalEntityAttributeExcpetion;
import com.athena.exception.MixedCopyTypeException;
import com.athena.model.Journal;
import com.athena.model.JournalCopy;
import com.athena.model.JournalPK;
import com.athena.repository.jpa.JournalRepository;
import com.athena.repository.jpa.copy.JournalCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tommy on 2017/9/2.
 */
@Service
public class JournalCopyService implements CopyService<JournalCopy, Long, JournalPK> {

    private final JournalCopyRepository journalCopyRepository;
    private final JournalRepository journalRepository;

    @Autowired
    public JournalCopyService(JournalCopyRepository journalCopyRepository, JournalRepository journalRepository) {
        this.journalCopyRepository = journalCopyRepository;
        this.journalRepository = journalRepository;
    }

    @Override
    public void addCopy(JournalCopy copy) {
        this.journalCopyRepository.save(copy);
    }

    @Override
    public void addCopies(List<JournalCopy> copies) {
        this.journalCopyRepository.save(copies);
    }

    @Override
    public JournalCopy getCopy(Long id) throws IdOfResourceNotFoundException {
        JournalCopy copy = this.journalCopyRepository.findByIdAndJournalIsNotNull(id);
        if (copy == null) {
            throw new IdOfResourceNotFoundException();
        }
        return copy;
    }

    @Override
    public List<JournalCopy> getCopies(List<Long> idList) {
        return this.journalCopyRepository.findByIdIsInAndJournalIsNotNull(idList);
    }

    /**
     * Get all copies of certain journal
     *
     * @param fkList the key of journal
     * @return
     * @throws IdOfResourceNotFoundException
     */
    @Override
    public List<JournalCopy> getCopies(JournalPK fkList) throws IdOfResourceNotFoundException {
        Journal journal = this.journalRepository.findOne(fkList);
        if (journal == null) {
            throw new IdOfResourceNotFoundException();
        }
        return this.journalCopyRepository.findByJournal(journal);
    }

    @Override
    public void deleteCopies(JournalPK journalPK) throws IdOfResourceNotFoundException {
        this.journalCopyRepository.delete(this.getCopies(journalPK));
    }

    @Override
    public void deleteCopy(Long id) {
        this.journalCopyRepository.delete(id);
    }

    @Override
    public void deleteCopies(List<Long> ids) throws MixedCopyTypeException {
        List<JournalCopy> copies = this.getCopies(ids);
        if (ids.size() != copies.size()) {
            throw new MixedCopyTypeException(JournalCopy.class);
        }
        this.journalCopyRepository.delete(copies);
    }

    @Override
    public void updateCopy(JournalCopy copy) {
        this.journalCopyRepository.update(copy);
    }

    @Override
    public void updateCopies(List<JournalCopy> copyList) throws IllegalEntityAttributeExcpetion {
        try {
            this.journalCopyRepository.update(copyList);
        } catch (Exception e) {
            throw new IllegalEntityAttributeExcpetion();
        }
    }

}
