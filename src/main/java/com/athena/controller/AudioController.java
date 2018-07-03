package com.athena.controller;

import com.athena.annotation.PublicationSearchParam;
import com.athena.model.publication.Audio;
import com.athena.model.publication.search.AudioSearchVo;
import com.athena.service.publication.AudioService;
import com.athena.service.util.BatchService;
import com.athena.service.util.PageableHeaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Tommy on 2018/3/24.
 */
@RestController
@RequestMapping("${web.url.prefix}/audios/**")
@Api(value = "Audio", description = "Manage audios")
public class AudioController {
    private AudioService audioService;
    private PageableHeaderService pageableHeaderService;
    private BatchService batchService;

    @Autowired
    public AudioController(AudioService audioService, PageableHeaderService pageableHeaderService, BatchService batchService) {
        this.audioService = audioService;
        this.pageableHeaderService = pageableHeaderService;
        this.batchService = batchService;
    }

    @ApiOperation(value = "search audio", response = Page.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "search success"),
            @ApiResponse(code = 401, message = "search term is missing")
    })
    @RequestMapping(path = "/**", method = RequestMethod.GET, produces = "application/json")
    public Page<Audio> search(@PublicationSearchParam AudioSearchVo searchVo, HttpServletRequest request, HttpServletResponse response) throws MissingServletRequestPartException {
        Page<Audio> searchResult = this.audioService.search(searchVo.getSpecification(), searchVo.getPageable());
        if (searchResult != null) {
            this.pageableHeaderService.setHeader(searchResult, request, response);
            return searchResult;
        }
        throw new MissingServletRequestPartException("missing term");
    }
}
