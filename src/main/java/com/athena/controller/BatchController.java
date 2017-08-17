package com.athena.controller;

import com.athena.model.Batch;
import com.athena.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<Batch> batch(@PathVariable String uuid) {
        //todo
        return ResponseEntity.badRequest().build();
    }
}
