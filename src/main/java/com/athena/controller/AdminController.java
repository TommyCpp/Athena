package com.athena.controller;

import com.athena.exception.http.*;
import com.athena.model.security.Account;
import com.athena.model.security.NewUserVo;
import com.athena.model.security.User;
import com.athena.service.security.PrivilegeService;
import com.athena.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Tommy on 2018/7/3.
 */
@RestController
@RequestMapping("${web.url.prefix}/users/**")
public class AdminController {
    private UserService userService;
    private String adminUrl;
    private PrivilegeService privilegeService;


    @Autowired
    public AdminController(UserService userService, @Value("${web.url}") String baseUrl, PrivilegeService privilegeService) {
        this.adminUrl = baseUrl + "/users";
        this.userService = userService;
        this.privilegeService = privilegeService;
    }

    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERADMIN')")
    public User get(@PathVariable("id") Integer id) throws UserNotFoundException {
        User user = this.userService.get(id);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException(Long.valueOf(id));
        }
    }

    @GetMapping("/**")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> get(@RequestParam MultiValueMap<String, String> params) throws UnsupportedHttpRequestParam {
        if (params.size() > 1 || params.get("id") == null) {
            // if there are params other than id
            Set keySet = params.keySet();
            keySet.remove("id");
            throw new UnsupportedHttpRequestParam((String[]) keySet.toArray(new String[0]));
        }
        List<Long> ids = params.get("id").stream().map(Integer::valueOf).map(Long::valueOf).collect(Collectors.toList());
        List<User> users = this.userService.get(ids);
        if (users.size() > 0) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> create(@RequestBody NewUserVo user) {
        User savedUser = this.userService.add(user);
        if (savedUser != null) {
            return ResponseEntity.created(URI.create(this.adminUrl + "/" + savedUser.getId())).build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id, @AuthenticationPrincipal Account account) throws ResourceNotFoundByIdException, InvalidCopyTypeException, ResourceNotDeletable, PermissionRequiredException {
        User user = this.userService.get(id);
        if (user == null) {
            throw new ResourceNotFoundByIdException();
        } else {
            if(!this.privilegeService.isCurrentUserCanOperateOn(user)){
                throw new PermissionRequiredException("cannot perform operation");
            }
        }
        this.userService.delete(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }

//    @PatchMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')|| hasRole('ROLE_SUPERADMIN')")
//    public ResponseEntity<?> update(@PathVariable Integer id) {
//    }



}
