package com.athena.service;

import com.athena.model.Book;
import com.athena.model.Copy;
import com.athena.model.CopyPK;
import com.athena.repository.jpa.BookRepository;
import com.athena.repository.jpa.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Tommy on 2017/8/24.
 */
@Service
public class CopyService {
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CopyService(CopyRepository copyRepository,BookRepository bookRepository) {
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
    }

    public void saveCopies(Long[] copyPKList) {
        Map<Long, Integer> indexMap = new HashMap<>();
        List<Copy> copyList = new ArrayList<>();
        for (Long isbn : copyPKList) {
            Copy copy = new Copy();
            if (indexMap.containsKey(isbn)) {
                copy.setId(new CopyPK(isbn, indexMap.get(isbn) + 1));
            } else {
                indexMap.put(isbn, 0);
                copy.setId(new CopyPK(isbn, indexMap.get(isbn) + 1));
            }
            copyList.add(copy);
        }
        this.copyRepository.save(copyList);
    }

    public void saveCopies(List<CopyPK> copyPKList) {
        List<Copy> copyList = new ArrayList<>();
        Map<Long, Set<CopyPK>> isbnCopyPK = this.divideCopyPKByIsbn(copyPKList);
        for (Long isbn: isbnCopyPK.keySet()) {
            Book book = this.bookRepository.findOne(isbn);
            for (CopyPK copyPK : isbnCopyPK.get(isbn)) {
                Copy copy = new Copy();
                copy.setId(copyPK);
                copy.setBook(book);
                copyList.add(copy);
            }
        }
        this.copyRepository.save(copyList);
    }


    private Map<Long, Set<CopyPK>> divideCopyPKByIsbn(List<CopyPK> copyPKList) {
        Map<Long, Set<CopyPK>> result = new HashMap<>();
        for (CopyPK copyPK : copyPKList) {
            Long isbn = copyPK.getIsbn();
            if (result.containsKey(isbn)) {
                // if the isbn has been saved
                Set<CopyPK> set = result.get(isbn);
                set.add(copyPK);
                result.put(isbn, set);
            }
            else{
                //if not
                Set<CopyPK> set = new HashSet<>();
                set.add(copyPK);
                result.put(isbn, set);
            }
        }
        return result;
    }
}
