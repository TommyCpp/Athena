package com.athena.controller;

import com.athena.exception.http.UserNotFoundException;
import com.athena.model.security.User;
import com.athena.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        }
        else{
            throw new UserNotFoundException(Long.valueOf(id));
        }
    }
}
