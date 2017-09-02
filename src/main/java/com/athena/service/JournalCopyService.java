package com.athena.service;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.model.JournalCopy;
import com.athena.repository.jpa.BookCopyRepository;
import com.athena.repository.jpa.BookRepository;
import com.athena.repository.jpa.JournalCopyRepository;
import com.athena.repository.jpa.SimpleCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Tommy on 2017/9/2.
 */
@Service
public class JournalCopyService extends CopyService {

    @Autowired
    public JournalCopyService(SimpleCopyRepository simpleCopyRepository, BookRepository bookRepository, BookCopyRepository bookCopyRepository, JournalCopyRepository journalCopyRepository) {
        super(simpleCopyRepository, bookRepository, bookCopyRepository, journalCopyRepository);
    }

    @Override
    public JournalCopy getCopy(Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        return (JournalCopy) super.getCopy(id, "Journal");
    }
}
