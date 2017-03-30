package com.athena.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tommy on 2017/3/16.
 */
@RequestMapping("/api/**")
@RestController
public class TestController {
    @RequestMapping(path = "/{data}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String index(@PathVariable(name = "data") int i) {
        return Integer.toString(i);
    }
}
