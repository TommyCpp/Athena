package com.athena.controller;

import com.athena.exception.IdOfResourceNotFoundException;
import com.athena.exception.IllegalEntityAttributeExcpetion;
import com.athena.exception.InvalidCopyTypeException;
import com.athena.exception.MixedCopyTypeException;
import com.athena.model.Copy;
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

    @ApiOperation(value = "get simple copy", response = Copy.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Invalid copy type")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCopy(@PathVariable Long id) throws IdOfResourceNotFoundException, InvalidCopyTypeException {
        Copy copy = this.simpleCopyService.getCopy(id);
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
        this.simpleCopyService.deleteCopy(id);
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
    public ResponseEntity<?> updateCopies(@RequestBody List<SimpleCopy> copies) throws IllegalEntityAttributeExcpetion, MixedCopyTypeException {
        this.simpleCopyService.updateCopies(copies);
        return ResponseEntity.noContent().build();
    }
}
