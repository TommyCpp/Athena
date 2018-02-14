package com.athena.controller;

import com.athena.service.copy.CopyDamageReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tommy on 2018/2/13.
 */
@RestController
@RequestMapping("${web.url.prefix}/copy-damage-report/**")
public class CopyDamageReportController {

    private CopyDamageReportService copyDamageReportService;

    @Autowired
    public CopyDamageReportController(CopyDamageReportService copyDamageReportService) {
        this.copyDamageReportService = copyDamageReportService;
    }

}
