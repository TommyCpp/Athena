package com.athena.controller;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.model.Copy;
import com.athena.service.BatchService;
import com.athena.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(name = "/{id}")
    public ResponseEntity<?> getCopy(@PathVariable Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        Copy copy = this.copyService.getCopy(id);
        return ResponseEntity.ok(copy);
    }

}
