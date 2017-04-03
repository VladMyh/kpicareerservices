package com.kpics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kpics.domain.Idea;
import com.kpics.service.IdeaService;
import com.kpics.web.rest.util.HeaderUtil;
import com.kpics.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Idea.
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
     * POST  /ideas : Create a new idea.
     *
     * @param idea the idea to create
     * @return the ResponseEntity with status 201 (Created) and with body the new idea, or with status 400 (Bad Request) if the idea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ideas")
    @Timed
    public ResponseEntity<Idea> createIdea(@Valid @RequestBody Idea idea) throws URISyntaxException {
        log.debug("REST request to save Idea : {}", idea);
        if (idea.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new idea cannot already have an ID")).body(null);
        }
        Idea result = ideaService.save(idea);
        return ResponseEntity.created(new URI("/api/ideas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ideas : Updates an existing idea.
     *
     * @param idea the idea to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated idea,
     * or with status 400 (Bad Request) if the idea is not valid,
     * or with status 500 (Internal Server Error) if the idea couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ideas")
    @Timed
    public ResponseEntity<Idea> updateIdea(@Valid @RequestBody Idea idea) throws URISyntaxException {
        log.debug("REST request to update Idea : {}", idea);
        if (idea.getId() == null) {
            return createIdea(idea);
        }
        Idea result = ideaService.save(idea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, idea.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ideas : get all the ideas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ideas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/ideas")
    @Timed
    public ResponseEntity<List<Idea>> getAllIdeas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Ideas");
        Page<Idea> page = ideaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ideas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ideas/:id : get the "id" idea.
     *
     * @param id the id of the idea to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the idea, or with status 404 (Not Found)
     */
    @GetMapping("/ideas/{id}")
    @Timed
    public ResponseEntity<Idea> getIdea(@PathVariable String id) {
        log.debug("REST request to get Idea : {}", id);
        Idea idea = ideaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(idea));
    }

    /**
     * DELETE  /ideas/:id : delete the "id" idea.
     *
     * @param id the id of the idea to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ideas/{id}")
    @Timed
    public ResponseEntity<Void> deleteIdea(@PathVariable String id) {
        log.debug("REST request to delete Idea : {}", id);
        ideaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
