package com.athena.controller;

import com.athena.exception.http.*;
import com.athena.model.copy.AbstractCopy;
import com.athena.model.copy.CopyStatus;
import com.athena.model.copy.CopyVerficationVo;
import com.athena.model.copy.SimpleCopy;
import com.athena.model.security.User;
import com.athena.service.copy.SimpleCopyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @ApiOperation(value = "get by publications simple copy", response = AbstractCopy.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Invalid copy type")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCopy(@PathVariable Long id) throws ResourceNotFoundByIdException, InvalidCopyTypeException {
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
            produces = "application/json"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid entity attribute"),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/")
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> updateCopies(@RequestBody List<SimpleCopy> copies) throws IllegalEntityAttributeException, MixedCopyTypeException {
        return ResponseEntity.ok(this.simpleCopyService.update(copies));
    }

    @ApiOperation(value = "partial update copy", authorizations = {
            @Authorization(
                    value = "admin/superadmin"
            ),
    },
            notes = "update copy success",
            produces = "application/json"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Copy does not contain certain field"),
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> partialUpdateCopies(@PathVariable Long id, @RequestBody Map<String, Object> params) throws IllegalEntityAttributeException, InvalidCopyTypeException, ResourceNotFoundException {
        return ResponseEntity.ok(this.simpleCopyService.partialUpdate(id, params));
    }


    @ApiOperation(value = "ify returned copy is not damaged", authorizations = {
            @Authorization(
                    value = "admin/superadmin"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "copy have been verified")
    })
    @PatchMapping(path = "/{id}/verify")
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> verifyCopy(@PathVariable Long id, @RequestBody CopyVerficationVo copyVerficationVo, @AuthenticationPrincipal User user) throws ResourceNotFoundByIdException {
        if (copyVerficationVo.getCopyStatus() == CopyStatus.DAMAGED) {
            //if damaged
            this.simpleCopyService.handleDamagedReturnCopy(user, id, copyVerficationVo.getDescription());
        } else {
            if (copyVerficationVo.getCopyStatus() == CopyStatus.AVAILABLE) {
                this.simpleCopyService.handleSoundReturnCopy(user, id);
            }
        }
        return ResponseEntity.ok(null);//todo: test
    }
}
