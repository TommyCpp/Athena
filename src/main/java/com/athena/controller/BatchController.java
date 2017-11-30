package com.athena.controller;

import com.athena.exception.http.ResourceNotFoundException;
import com.athena.model.Batch;
import com.athena.security.model.Account;
import com.athena.security.service.AccountService;
import com.athena.service.util.BatchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tommy on 2017/8/17.
 */
@RestController
@RequestMapping("${web.url.prefix}/batch/**")
public class BatchController {
    private final BatchService batchService;
    private final AccountService accountService;

    @Autowired
    public BatchController(BatchService batchService, AccountService accountService) {
        this.batchService = batchService;
        this.accountService = accountService;
    }


    @ApiOperation(value = "getByPublications batch", response = Batch.class)
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<Batch> batch(@PathVariable String uuid, @AuthenticationPrincipal Account user) throws ResourceNotFoundException {
        if (this.accountService.privilegeBigger(user, "ROLE_READER")) {
            // if it is admin or above
            return ResponseEntity.ok(this.batchService.findOne(uuid));
        } else {
            // if it is a reader or low
            return ResponseEntity.ok(this.batchService.findOne(uuid, "ROLE_READER"));
        }
    }
}
