package com.athena.controller;

import com.athena.exception.http.IdOfResourceNotFoundException;
import com.athena.exception.http.IllegalEntityAttributeException;
import com.athena.exception.http.InvalidCopyTypeException;
import com.athena.exception.http.MixedCopyTypeException;
import com.athena.model.AbstractCopy;
import com.athena.model.SimpleCopy;
import com.athena.service.copy.SimpleCopyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    public CopyController(SimpleCopyService simpleCopyService) {
        this.simpleCopyService = simpleCopyService;
    }

    @ApiOperation(value = "getByPublications simple copy", response = AbstractCopy.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Invalid copy type")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCopy(@PathVariable Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        SimpleCopy copy = this.simpleCopyService.get(id);
        return ResponseEntity.ok(copy);
    }

    @ApiOperation(value = "delete copy", authorizations = {
            @Authorization(
                    value = "admin/superadmin"
            )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> deleteCopy(@PathVariable Long id) {
        this.simpleCopyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @ApiOperation(value = "update copy", authorizations = {
            @Authorization(
                    value = "admin/superadmin"
            ),
    },
            notes = "update copy success",
            produces = "application/text"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid entity attribute"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/")
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> updateCopies(@RequestBody List<SimpleCopy> copies) throws IllegalEntityAttributeException, MixedCopyTypeException {
        this.simpleCopyService.update(copies);
        return ResponseEntity.noContent().build();
    }
}
