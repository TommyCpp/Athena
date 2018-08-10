package com.athena.controller;

import com.athena.exception.http.UnsupportedHttpRequestParam;
import com.athena.exception.http.UserNotFoundException;
import com.athena.model.security.User;
import com.athena.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPERADMIN')")
    public User get(@PathVariable("id") Integer id) throws UserNotFoundException {
        User user = this.userService.get(id);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException(Long.valueOf(id));
        }
    }

    @GetMapping("/")
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

}
