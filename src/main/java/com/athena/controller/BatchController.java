package com.athena.controller;

import com.athena.exception.ResourceNotFoundException;
import com.athena.model.Batch;
import com.athena.security.model.Account;
import com.athena.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Tommy on 2017/8/17.
 */
@RestController
@RequestMapping("${web.url.prefix}/batch/**")
public class BatchController {
    private final BatchService batchService;

    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<Batch> batch(@PathVariable String uuid, @AuthenticationPrincipal Account user) throws ResourceNotFoundException {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // if it is admin
            return ResponseEntity.ok(this.batchService.findOne(uuid));
        }
        return ResponseEntity.ok(this.batchService.findOne(uuid, "ROLE_READER"));
    }
}
