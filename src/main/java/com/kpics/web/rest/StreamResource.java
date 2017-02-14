package com.kpics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kpics.domain.Stream;
import com.kpics.service.StreamService;
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
 * REST controller for managing Stream.
 */
@RestController
@RequestMapping("/api")
public class StreamResource {

    private final Logger log = LoggerFactory.getLogger(StreamResource.class);

    private static final String ENTITY_NAME = "stream";
        
    private final StreamService streamService;

    public StreamResource(StreamService streamService) {
        this.streamService = streamService;
    }

    /**
     * POST  /streams : Create a new stream.
     *
     * @param stream the stream to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stream, or with status 400 (Bad Request) if the stream has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/streams")
    @Timed
    public ResponseEntity<Stream> createStream(@Valid @RequestBody Stream stream) throws URISyntaxException {
        log.debug("REST request to save Stream : {}", stream);
        if (stream.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new stream cannot already have an ID")).body(null);
        }
        Stream result = streamService.save(stream);
        return ResponseEntity.created(new URI("/api/streams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /streams : Updates an existing stream.
     *
     * @param stream the stream to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stream,
     * or with status 400 (Bad Request) if the stream is not valid,
     * or with status 500 (Internal Server Error) if the stream couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/streams")
    @Timed
    public ResponseEntity<Stream> updateStream(@Valid @RequestBody Stream stream) throws URISyntaxException {
        log.debug("REST request to update Stream : {}", stream);
        if (stream.getId() == null) {
            return createStream(stream);
        }
        Stream result = streamService.save(stream);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stream.getId().toString()))
            .body(result);
    }

    /**
     * GET  /streams : get all the streams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of streams in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/streams")
    @Timed
    public ResponseEntity<List<Stream>> getAllStreams(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Streams");
        Page<Stream> page = streamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/streams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /streams/:id : get the "id" stream.
     *
     * @param id the id of the stream to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stream, or with status 404 (Not Found)
     */
    @GetMapping("/streams/{id}")
    @Timed
    public ResponseEntity<Stream> getStream(@PathVariable String id) {
        log.debug("REST request to get Stream : {}", id);
        Stream stream = streamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stream));
    }

    /**
     * DELETE  /streams/:id : delete the "id" stream.
     *
     * @param id the id of the stream to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/streams/{id}")
    @Timed
    public ResponseEntity<Void> deleteStream(@PathVariable String id) {
        log.debug("REST request to delete Stream : {}", id);
        streamService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
