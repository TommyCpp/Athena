package com.athena.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tommy on 2018/3/20.
 */
@RestController
@RequestMapping("${web.url.prefix}/journals/**")
@Api(value = "Journal", description = "Manage journals")
public class JournalController {
}
