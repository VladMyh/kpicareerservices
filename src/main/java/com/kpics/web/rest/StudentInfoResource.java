package com.kpics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kpics.domain.StudentInfo;
import com.kpics.service.UserService;
import com.kpics.service.dto.UserDTO;
import com.kpics.web.rest.util.HeaderUtil;
import com.kpics.web.rest.util.PaginationUtil;
import com.kpics.web.rest.vm.StudentVM;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing StudentInfo.
 */
@RestController
@RequestMapping("/api")
public class StudentInfoResource {

    private final Logger log = LoggerFactory.getLogger(StudentInfoResource.class);

    private static final String ENTITY_NAME = "studentInfo";

    private final StudentInfoService studentInfoService;

    private final UserService userService;

    public StudentInfoResource(StudentInfoService studentInfoService, UserService userService) {
        this.studentInfoService = studentInfoService;
        this.userService = userService;
    }

    /**
     * POST  /student-infos : Create a new studentInfo.
     *
     * @param studentInfo the studentInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentInfo, or with status 400 (Bad Request) if the studentInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-infos")
    @Timed
    public ResponseEntity<StudentInfo> createStudentInfo(@Valid @RequestBody StudentInfo studentInfo) throws URISyntaxException {
        log.debug("REST request to save StudentInfo : {}", studentInfo);
        if (studentInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new studentInfo cannot already have an ID")).body(null);
        }
        StudentInfo result = studentInfoService.save(studentInfo);
        return ResponseEntity.created(new URI("/api/student-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-infos : Updates an existing studentInfo.
     *
     * @param studentInfo the studentInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentInfo,
     * or with status 400 (Bad Request) if the studentInfo is not valid,
     * or with status 500 (Internal Server Error) if the studentInfo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-infos")
    @Timed
    public ResponseEntity<StudentInfo> updateStudentInfo(@Valid @RequestBody StudentInfo studentInfo) throws URISyntaxException {
        log.debug("REST request to update StudentInfo : {}", studentInfo);
        if (studentInfo.getId() == null) {
            return createStudentInfo(studentInfo);
        }
        StudentInfo result = studentInfoService.save(studentInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-infos : get all the studentInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentInfos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/student-infos")
    @Timed
    public ResponseEntity<List<StudentVM>> getAllStudentInfos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of StudentInfos");
        Page<StudentInfo> page = studentInfoService.findAll(pageable);
        List<StudentVM> result = page.getContent().stream()
            .map(o -> new StudentVM(new UserDTO(userService.getUserWithAuthorities(o.getUserId())), o))
            .collect(Collectors.toList());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-infos");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    /**
     * GET  /student-infos/:id : get the "id" studentInfo.
     *
     * @param id the id of the studentInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentInfo, or with status 404 (Not Found)
     */
    @GetMapping("/student-infos/{id}")
    @Timed
    public ResponseEntity<StudentVM> getStudentInfo(@PathVariable String id) {
        log.debug("REST request to get StudentInfo : {}", id);
        StudentInfo studentInfo = studentInfoService.findOne(id);
        StudentVM studentVM = null;

        if(studentInfo != null) {
            UserDTO userDTO = new UserDTO(userService.getUserWithAuthorities(studentInfo.getUserId()));
            studentVM = new StudentVM(userDTO, studentInfo);
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentVM));
    }

    /**
     * DELETE  /student-infos/:id : delete the "id" studentInfo.
     *
     * @param id the id of the studentInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentInfo(@PathVariable String id) {
        log.debug("REST request to delete StudentInfo : {}", id);
        studentInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
