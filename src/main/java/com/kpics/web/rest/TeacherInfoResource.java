package com.kpics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kpics.domain.TeacherInfo;
import com.kpics.service.TeacherInfoService;
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
 * REST controller for managing TeacherInfo.
 */
@RestController
@RequestMapping("/api")
public class TeacherInfoResource {

    private final Logger log = LoggerFactory.getLogger(TeacherInfoResource.class);

    private static final String ENTITY_NAME = "teacherInfo";
        
    private final TeacherInfoService teacherInfoService;

    public TeacherInfoResource(TeacherInfoService teacherInfoService) {
        this.teacherInfoService = teacherInfoService;
    }

    /**
     * POST  /teacher-infos : Create a new teacherInfo.
     *
     * @param teacherInfo the teacherInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teacherInfo, or with status 400 (Bad Request) if the teacherInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/teacher-infos")
    @Timed
    public ResponseEntity<TeacherInfo> createTeacherInfo(@Valid @RequestBody TeacherInfo teacherInfo) throws URISyntaxException {
        log.debug("REST request to save TeacherInfo : {}", teacherInfo);
        if (teacherInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new teacherInfo cannot already have an ID")).body(null);
        }
        TeacherInfo result = teacherInfoService.save(teacherInfo);
        return ResponseEntity.created(new URI("/api/teacher-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teacher-infos : Updates an existing teacherInfo.
     *
     * @param teacherInfo the teacherInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teacherInfo,
     * or with status 400 (Bad Request) if the teacherInfo is not valid,
     * or with status 500 (Internal Server Error) if the teacherInfo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/teacher-infos")
    @Timed
    public ResponseEntity<TeacherInfo> updateTeacherInfo(@Valid @RequestBody TeacherInfo teacherInfo) throws URISyntaxException {
        log.debug("REST request to update TeacherInfo : {}", teacherInfo);
        if (teacherInfo.getId() == null) {
            return createTeacherInfo(teacherInfo);
        }
        TeacherInfo result = teacherInfoService.save(teacherInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teacherInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teacher-infos : get all the teacherInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teacherInfos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/teacher-infos")
    @Timed
    public ResponseEntity<List<TeacherInfo>> getAllTeacherInfos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TeacherInfos");
        Page<TeacherInfo> page = teacherInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teacher-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /teacher-infos/:id : get the "id" teacherInfo.
     *
     * @param id the id of the teacherInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teacherInfo, or with status 404 (Not Found)
     */
    @GetMapping("/teacher-infos/{id}")
    @Timed
    public ResponseEntity<TeacherInfo> getTeacherInfo(@PathVariable String id) {
        log.debug("REST request to get TeacherInfo : {}", id);
        TeacherInfo teacherInfo = teacherInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teacherInfo));
    }

    /**
     * DELETE  /teacher-infos/:id : delete the "id" teacherInfo.
     *
     * @param id the id of the teacherInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/teacher-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeacherInfo(@PathVariable String id) {
        log.debug("REST request to delete TeacherInfo : {}", id);
        teacherInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
