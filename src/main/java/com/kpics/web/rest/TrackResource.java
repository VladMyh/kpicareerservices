package com.kpics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kpics.domain.Track;
import com.kpics.service.TrackService;
import com.kpics.web.rest.util.HeaderUtil;
import com.kpics.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Track.
 */
@RestController
@RequestMapping("/api")
public class TrackResource {

    private final Logger log = LoggerFactory.getLogger(TrackResource.class);

    private static final String ENTITY_NAME = "track";

    private final TrackService trackService;

    public TrackResource(TrackService trackService) {
        this.trackService = trackService;
    }

    /**
     * POST  /tracks : Create a new track.
     *
     * @param track the track to create
     * @return the ResponseEntity with status 201 (Created) and with body the new track, or with status 400 (Bad Request) if the track has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tracks")
    @Timed
    public ResponseEntity<Track> createTrack(@Valid @RequestBody Track track) throws URISyntaxException {
        log.debug("REST request to save Track : {}", track);
        if (track.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new track cannot be created already have an ID")).body(null);
        }
        if(trackService.findByName(track.getName()).isPresent()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "nameexists", "A new track cannot be created, name already used")).body(null);
        }
        track = track.teacherIds(new HashSet<>()).studentIds(new HashSet<>());
        Track result = trackService.save(track);
        return ResponseEntity.created(new URI("/api/tracks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tracks : Updates an existing track.
     *
     * @param track the track to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated track,
     * or with status 400 (Bad Request) if the track is not valid,
     * or with status 500 (Internal Server Error) if the track couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tracks")
    @Timed
    public ResponseEntity<Track> updateTrack(@Valid @RequestBody Track track) throws URISyntaxException {
        log.debug("REST request to update Track : {}", track);
        if (track.getId() == null) {
            return createTrack(track);
        }
        Track result = trackService.save(track);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, track.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tracks : get all the tracks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tracks in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tracks")
    @Timed
    public ResponseEntity<List<Track>> getAllTracks(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tracks");
        Page<Track> page = trackService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tracks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tracks/:id : get the "id" track.
     *
     * @param id the id of the track to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the track, or with status 404 (Not Found)
     */
    @GetMapping("/tracks/{id}")
    @Timed
    public ResponseEntity<Track> getTrack(@PathVariable String id) {
        log.debug("REST request to get Track : {}", id);
        Track track = trackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(track));
    }

    /**
     * DELETE  /tracks/:id : delete the "id" track.
     *
     * @param id the id of the track to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tracks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrack(@PathVariable String id) {
        log.debug("REST request to delete Track : {}", id);
        trackService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PutMapping("/tracks/{id}/teacher/{teacherId}")
    @Timed
    public ResponseEntity<Void> addTeacherToTrack(@PathVariable String id, @PathVariable String teacherId) {
        log.debug("Adding teacher id to track", id, teacherId);

        Track track = trackService.findOne(id);
        track.getTeacherIds().add(teacherId);
        trackService.save(track);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id)).build();
    }

}
