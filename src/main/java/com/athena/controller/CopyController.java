package com.athena.controller;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.IllegalEntityAttributeExcpetion;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.model.Copy;
import com.athena.model.SimpleCopy;
import com.athena.service.BatchService;
import com.athena.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Tommy on 2017/8/24.
 */
@RestController
@RequestMapping("${web.url.prefix}/copy/**")
public class CopyController {

    private final CopyService simpleCopyService;
    private final String copyUrl;
    private final BatchService batchService;
    private final String baseUrl;

    @Autowired
    public CopyController(CopyService copyService, @Value("${web.url}") String baseUrl, BatchService batchService) {
        this.simpleCopyService = copyService;
        this.baseUrl = baseUrl;
        this.copyUrl = baseUrl + "/copy";
        this.batchService = batchService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCopy(@PathVariable Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        Copy copy = this.simpleCopyService.getCopy(id);
        return ResponseEntity.ok(copy);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCopy(@PathVariable Long id) {
        this.simpleCopyService.deleteCopy(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/")
    public ResponseEntity<?> updateCopies(@RequestBody List<SimpleCopy> copies) throws IllegalEntityAttributeExcpetion {
        this.simpleCopyService.updateCopies(copies);
        return ResponseEntity.ok().build();
    }
}
