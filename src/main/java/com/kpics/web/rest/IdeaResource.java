package com.kpics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kpics.domain.Idea;
import com.kpics.domain.Stream;
import com.kpics.service.IdeaService;
import com.kpics.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Ideas.
 */
@RestController
@RequestMapping("/api")
public class IdeaResource {

    private final Logger log = LoggerFactory.getLogger(IdeaResource.class);

    private static final String ENTITY_NAME = "idea";

    private final IdeaService ideaService;

    public IdeaResource(IdeaService ideaService) {
        this.ideaService = ideaService;
    }


    /**
     * GET  /ideas : get all the ideas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of streams in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/ideas")
    @Timed
    public ResponseEntity<List<Idea>> getAllIdeas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ideas");
        Page<Idea> page = ideaService.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/streams");
        return new ResponseEntity<>(null, headers, HttpStatus.OK);
    }

}
