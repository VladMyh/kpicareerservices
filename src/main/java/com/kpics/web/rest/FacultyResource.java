package com.kpics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kpics.domain.Department;
import com.kpics.domain.Faculty;
import com.kpics.service.FacultyService;
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
 * REST controller for managing Faculty.
 */
@RestController
@RequestMapping("/api")
public class FacultyResource {

    private final Logger log = LoggerFactory.getLogger(FacultyResource.class);

    private static final String FACULTY = "faculty";

    private static final String DEPARTMENT = "department";

    private final FacultyService facultyService;

    public FacultyResource(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    /**
     * POST  /faculties : Create a new faculty.
     *
     * @param faculty the faculty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new faculty, or with status 400 (Bad Request) if the faculty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/faculties")
    @Timed
    public ResponseEntity<Faculty> createFaculty(@Valid @RequestBody Faculty faculty) throws URISyntaxException {
        log.debug("REST request to save Faculty : {}", faculty);
        if (faculty.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(FACULTY, "idexists", "A new faculty cannot already have an ID")).body(null);
        }
        Faculty result = facultyService.save(faculty);
        return ResponseEntity.created(new URI("/api/faculties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(FACULTY, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /faculties : Updates an existing faculty.
     *
     * @param faculty the faculty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faculty,
     * or with status 400 (Bad Request) if the faculty is not valid,
     * or with status 500 (Internal Server Error) if the faculty couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/faculties")
    @Timed
    public ResponseEntity<Faculty> updateFaculty(@Valid @RequestBody Faculty faculty) throws URISyntaxException {
        log.debug("REST request to update Faculty : {}", faculty);
        if (faculty.getId() == null) {
            return createFaculty(faculty);
        }
        Faculty result = facultyService.save(faculty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(FACULTY, faculty.getId().toString()))
            .body(result);
    }

    /**
     * GET  /faculties : get all the faculties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of faculties in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/faculties/all")
    @Timed
    public ResponseEntity<List<Faculty>> getAllFaculties(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Faculties");
        Page<Faculty> page = facultyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/faculties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /faculties/:id : get the "id" faculty.
     *
     * @param id the id of the faculty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the faculty, or with status 404 (Not Found)
     */
    @GetMapping("/faculties/{id}")
    @Timed
    public ResponseEntity<Faculty> getFaculty(@PathVariable String id) {
        log.debug("REST request to get Faculty : {}", id);
        Faculty faculty = facultyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(faculty));
    }

    /**
     * DELETE  /faculties/:id : delete the "id" faculty.
     *
     * @param id the id of the faculty to delete
     * @return   the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/faculties/{id}")
    @Timed
    public ResponseEntity<Void> deleteFaculty(@PathVariable String id) {
        log.debug("REST request to delete Faculty : {}", id);
        if(facultyService.delete(id)) {
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(FACULTY, id.toString())).build();
        }
        else {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(FACULTY, "entityinuse", "Cannot delete department!"))
                .body(null);
        }
    }


    /**
     * POST  /faculties/:id/departments : Create a new department.
     *
     * @param id           Faculty id.
     * @param department   the Department to create
     * @return             the ResponseEntity with status 201 (Created) and with body faculty, or with status 400 (Bad Request) if the faculty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/faculties/{id}/departments")
    @Timed
    public ResponseEntity<Faculty> createDepartment(@PathVariable String id,
                                                    @Valid @RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to save create Department, facultyId: {}, department: {}", id, department);
        if (department.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(DEPARTMENT, "idexists", "A new department cannot already have an ID")).body(null);
        }
        Faculty result = facultyService.saveDepartment(id, department);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(DEPARTMENT, id))
            .body(result);
    }

    /**
     * PUT  /faculties/:facultyId/departments/:departmentId : Updates an existing department.
     *
     * @param facultyId    Faculty id.
     * @param departmentId Department id.
     * @param department   the faculty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faculty,
     * or with status 400 (Bad Request) if the department is not valid,
     * or with status 500 (Internal Server Error) if the faculty couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/faculties/{facultyId}/departments/{departmentId}")
    @Timed
    public ResponseEntity<Faculty> updateDepartment(@PathVariable String facultyId,
                                                    @PathVariable String departmentId,
                                                    @Valid @RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to update Department, facultyId: {}, departmentId: {}, department: {}", facultyId, departmentId, department);
        if (department.getId() == null) {
            return createDepartment(facultyId, department);
        }
        Faculty result = facultyService.saveDepartment(facultyId, department);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(DEPARTMENT, facultyId))
            .body(result);
    }

    /**
     * DELETE  /faculties/:facultyId/departments/:departmentId : delete the department.
     *
     * @param facultyId    Faculty id.
     * @param departmentId The id of the department to delete.
     * @return             the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/faculties/{facultyId}/departments/{departmentId}")
    @Timed
    public ResponseEntity<Void> deleteDepartment(@PathVariable String facultyId,
                                                 @PathVariable String departmentId) {
        log.debug("REST request to delete Department, facultyId: {}, departmentId: {}", facultyId, departmentId);

        if(facultyService.deleteDepartment(facultyId, departmentId)) {
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(DEPARTMENT, facultyId)).build();
        }
        else {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(DEPARTMENT, "entityinuse", "Cannot delete department!"))
                .body(null);
        }
    }

    /**
     * GET  /faculties/:facultyId/departments/:departmentId : get the department of the faculty.
     *
     * @param facultyId    the id of the stream
     * @param departmentId the id of the track
     * @return             the ResponseEntity with status 200 (OK) and with body the stream, or with status 404 (Not Found)
     */
    @GetMapping("/faculties/{facultyId}/departments/{departmentId}")
    @Timed
    public ResponseEntity<Department> getDepartment(@PathVariable String facultyId,
                                                    @PathVariable String departmentId) {
        log.debug("REST request to get department, faculty: {}, department: {}", facultyId, departmentId);
        return ResponseEntity.ok().body(facultyService.findDepartment(facultyId, departmentId));
    }
}
