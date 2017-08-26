package com.athena.controller;

import com.athena.exception.BookNotFoundException;
import com.athena.model.Batch;
import com.athena.model.Copy;
import com.athena.model.CopyPK;
import com.athena.service.BatchService;
import com.athena.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Batch> createCopies(@RequestBody List<CopyPK> copyPKList) throws BookNotFoundException, URISyntaxException {
        List<Copy> copyList = this.copyService.saveCopies(copyPKList);
        List<String> copyUrlList = new ArrayList<>();
        for (Copy copy : copyList) {
            copyUrlList.add(this.copyUrl + "/" + copy.getId().toString());
        }
        //Create Batch
        Batch batch = new Batch(UUID.randomUUID().toString(), "Copy", Calendar.getInstance().getTime(), copyUrlList);
        this.batchService.save(batch);
        return ResponseEntity.created(new URI(this.baseUrl + "/batch/" + batch.getId())).build();
    }
}
