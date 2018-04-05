package com.athena.controller;

import com.athena.annotation.PublicationSearchParam;
import com.athena.model.publication.Journal;
import com.athena.model.publication.search.JournalSearchVo;
import com.athena.service.publication.JournalService;
import com.athena.service.util.BatchService;
import com.athena.service.util.PageableHeaderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Tommy on 2018/3/20.
 */
@RestController
@RequestMapping("${web.url.prefix}/journals/**")
@Api(value = "Journal", description = "Manage journals")
public class JournalController {
    private JournalService journalService;
    private PageableHeaderService pageableHeaderService;
    private BatchService batchService;

    @Autowired
    public JournalController(JournalService journalService, PageableHeaderService pageableHeaderService, BatchService batchService) {
        this.journalService = journalService;
        this.pageableHeaderService = pageableHeaderService;
        this.batchService = batchService;
    }


    @ApiOperation(value = "search journal")
    @ApiResponses({
            @ApiResponse(code = 200, message = "search success"),
            @ApiResponse(code = 401, message = "search term is missing")
    })
    @RequestMapping(path = "/**", method = RequestMethod.GET, produces = "application/json")
    public Page<Journal> search(
            @ApiParam(name = "journalSearchVo", value = "journal search vo which contains the info regarding the search")
            @PublicationSearchParam JournalSearchVo journalSearchVo,
            HttpServletResponse response,
            HttpServletRequest request
    ) throws MissingServletRequestPartException {
        Page<Journal> result = this.journalService.search(journalSearchVo.getSpecification(), journalSearchVo.getPageable());
        if (result != null) {
            this.pageableHeaderService.setHeader(result, request, response);
            return result;
        }
        throw new MissingServletRequestPartException("search term");
    }


}
