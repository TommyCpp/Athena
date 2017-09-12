package com.athena.controller;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.IllegalEntityAttributeExcpetion;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.exception.MixedCopyTypeException;
import com.athena.model.Copy;
import com.athena.model.SimpleCopy;
import com.athena.service.BatchService;
import com.athena.service.SimpleCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Tommy on 2017/8/24.
 */
@RestController
@RequestMapping("${web.url.prefix}/copy/**")
public class CopyController {

    private final SimpleCopyService simpleCopyService;
    private final String copyUrl;
    private final BatchService batchService;
    private final String baseUrl;

    @Autowired
    public CopyController(SimpleCopyService simpleCopyService, @Value("${web.url}") String baseUrl, BatchService batchService) {
        this.simpleCopyService = simpleCopyService;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> deleteCopy(@PathVariable Long id) {
        this.simpleCopyService.deleteCopy(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/")
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> updateCopies(@RequestBody List<SimpleCopy> copies) throws IllegalEntityAttributeExcpetion, MixedCopyTypeException {
        this.simpleCopyService.updateCopies(copies);
        return ResponseEntity.noContent().build();
    }
}
