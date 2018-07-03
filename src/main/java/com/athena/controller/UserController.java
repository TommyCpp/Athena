package com.athena.controller;

import com.athena.model.security.Account;
import com.athena.model.security.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tommy on 2018/1/16.
 */
@RestController
@RequestMapping("${web.url.prefix}/user/**")
public class UserController {

    @ApiOperation(value = "get current user info", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(response = User.class, code = 200, message = "current User"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @GetMapping("/")
    public User getCurrentUser(Authentication authentication) {
        return ((Account) authentication.getPrincipal()).getUser();
    }

}
