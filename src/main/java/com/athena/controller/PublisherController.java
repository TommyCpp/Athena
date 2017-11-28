package com.athena.controller;

import com.athena.exception.http.ResourceNotDeletable;
import com.athena.exception.http.ResourceNotFoundByIdException;
import com.athena.exception.internal.EntityAttributeNotFoundException;
import com.athena.model.Publisher;
import com.athena.service.PublisherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

/**
 * Created by Tommy on 2017/10/22.
 */
@RestController
@RequestMapping("${web.url.prefix}/publishers/**")
@Api(value = "Publisher", description = "Manage publisher")
public class PublisherController {

    private final PublisherService publisherService;
    private final String baseUrl;
    private final String publisherUrl;


    @Autowired
    public PublisherController(PublisherService publisherService, @Value("${web.url}") String baseUrl) {
        this.publisherService = publisherService;
        this.baseUrl = baseUrl;
        this.publisherUrl = baseUrl + "/publishers";
    }

    @ApiOperation(value = "add publisher", response = Void.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "created")
    })
    @RequestMapping(path = "/**", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole({'ROLE_ADMIN','ROLE_SUPERADMIN'})")
    public ResponseEntity<?> create(@RequestBody Publisher publisher) {
        publisher = this.publisherService.add(publisher);
        return ResponseEntity.created(URI.create(this.publisherUrl + "/" + publisher.getId())).build();
    }


    @ApiOperation(value = "delete publisher", response = Void.class)
    @ApiResponses({
            @ApiResponse(code = 204, message = "deleted"),
            @ApiResponse(code = 404, message = "id of publisher is not exist"),
            @ApiResponse(code = 403, message = "some of relate resource is not deleteable, thus the publisher cannot be deleted")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @PreAuthorize("hasAnyRole({'ROLE_ADMIN','ROLE_SUPERADMIN'})")
    public ResponseEntity<?> delete(@PathVariable String id) throws ResourceNotDeletable, ResourceNotFoundByIdException {
        this.publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "partial update publisher", response = Publisher.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "updated"),
            @ApiResponse(code = 404, message = "id of publisher is not exist"),
            @ApiResponse(code = 400, message = "attribute is not exist")
    })
    @RequestMapping(path = "/{id}", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole({'ROLE_ADMIN','ROLE_SUPERADMIN'})")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Map<String, Object> attributeKVs) throws EntityAttributeNotFoundException, ResourceNotFoundByIdException {
        Publisher publisher = this.publisherService.update(id, attributeKVs.entrySet());
        return ResponseEntity.ok(publisher);
    }

    @ApiOperation(value = "update publisher", response = Publisher.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "updated"),
            @ApiResponse(code = 404, message = "id of publisher is not exist")
    })
    @RequestMapping(path = "/**", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAnyRole({'ROLE_SUPERADMIN','ROLE_ADMIN'})")
    public ResponseEntity<?> update(@RequestBody Publisher publisher) throws ResourceNotFoundByIdException {
        return ResponseEntity.ok(this.publisherService.update(publisher));
    }

    @ApiOperation(value = "get publisher", response = Publisher.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "get"),
            @ApiResponse(code = 404, message = "id of publisher is not exist")
    })
    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> get(@PathVariable String id) throws ResourceNotFoundByIdException {
        return ResponseEntity.ok(this.publisherService.get(id));
    }

}
