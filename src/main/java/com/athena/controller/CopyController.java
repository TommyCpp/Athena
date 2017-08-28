package com.athena.controller;

import com.athena.exception.BatchStoreException;
import com.athena.exception.BookNotFoundException;
import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.model.Batch;
import com.athena.model.Copy;
import com.athena.model.CopyPK;
import com.athena.service.BatchService;
import com.athena.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tommy on 2017/8/24.
 */
@RestController
@RequestMapping("${web.url.prefix}/copy/**")
public class CopyController {

    private final CopyService copyService;
    private final String copyUrl;
    private final BatchService batchService;
    private final String baseUrl;

    @Autowired
    public CopyController(CopyService copyService, @Value("${web.url}") String baseUrl, BatchService batchService) {
        this.copyService = copyService;
        this.baseUrl = baseUrl;
        this.copyUrl = baseUrl + "/copy";
        this.batchService = batchService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<Batch> createCopies(@RequestBody List<CopyPK> copyPKList) throws BookNotFoundException, URISyntaxException, BatchStoreException {
        List<Copy> copyList = this.copyService.saveCopies(copyPKList);
        List<String> copyUrlList = new ArrayList<>();
        for (Copy copy : copyList) {
            copyUrlList.add(this.copyUrl + "/" + copy.getId().toString());
        }
        //Create Batch
        Batch batch = new Batch(UUID.randomUUID().toString(), "Copy", Calendar.getInstance().getTime(), copyUrlList);
        try {
            this.batchService.save(batch);
        } catch (DataAccessException e) {
            throw new BatchStoreException(copyList, "Copy");
        }
        return ResponseEntity.created(new URI(this.baseUrl + "/batch/" + batch.getId())).build();
    }

    @RequestMapping(value = "/{isbn}/{id}", method = RequestMethod.GET)
    public ResponseEntity<Copy> getCopy(@PathVariable("isbn") Long isbn, @PathVariable("id") Integer id) throws IdOfResourceNotFoundException {
        Copy copy = this.copyService.getCopy(isbn, id);
        return ResponseEntity.ok(copy);
    }

    @RequestMapping(value = "/{isbn}/**", method = RequestMethod.GET)
    public ResponseEntity<List<Copy>> getCopies(@PathVariable Long isbn) throws BookNotFoundException {
        List<Copy> copyList = this.copyService.getCopies(isbn);
        return ResponseEntity.ok(copyList);
    }
}
