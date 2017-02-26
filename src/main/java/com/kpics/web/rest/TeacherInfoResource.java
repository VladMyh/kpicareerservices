package com.kpics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kpics.domain.Authority;
import com.kpics.domain.TeacherInfo;
import com.kpics.domain.User;
import com.kpics.security.AuthoritiesConstants;
import com.kpics.service.UserService;
import com.kpics.service.dto.UserDTO;
import com.kpics.web.rest.util.HeaderUtil;
import com.kpics.web.rest.util.PaginationUtil;
import com.kpics.web.rest.vm.TeacherVM;
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
 * REST controller for managing TeacherInfo.
 */
@RestController
@RequestMapping("/api")
public class TeacherInfoResource {

    private final Logger log = LoggerFactory.getLogger(TeacherInfoResource.class);

    private static final String ENTITY_NAME = "teacherInfo";

    private final UserService userService;

    public TeacherInfoResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * PUT  /teacher-infos : Updates an existing teacher.
     *
     * @param userDTO the userDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teacherInfo,
     * or with status 400 (Bad Request) if the teacherInfo is not valid,
     * or with status 500 (Internal Server Error) if the teacherInfo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/teacher-infos")
    @Timed
    public ResponseEntity<UserDTO> updateTeacher(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to update teacher : {}", userDTO);

        Optional<UserDTO> result = userService.getTeacherById(userDTO.getId());
        result.ifPresent(u -> userService.updateUser(userDTO));

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.orElse(null).getId()))
            .body(result.get());
    }

    /**
     * GET  /teacher-infos : get all the teachers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teacherInfos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/teacher-infos")
    @Timed
    public ResponseEntity<List<TeacherVM>> getAllTeachers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Teachers");
        Page<UserDTO> page = userService.getAllTeachers(pageable);
        List<TeacherVM> result = page.getContent().stream()
            .map(TeacherVM::new)
            .collect(Collectors.toList());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teacher-infos");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    /**
     * GET  /teacher-infos/:id : get the teacher by "id".
     *
     * @param id the id of the teacher to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teacherInfo, or with status 404 (Not Found)
     */
    @GetMapping("/teacher-infos/{id}")
    @Timed
    public ResponseEntity<TeacherVM> getTeacher(@PathVariable String id) {
        log.debug("REST request to get Teacher : {}", id);
        Optional<UserDTO> teacher = userService.getTeacherById(id);
        TeacherVM teacherVM = null;

        if(teacher.isPresent()) {
            teacherVM = new TeacherVM(teacher.get());
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teacherVM));
    }

    /**
     * DELETE  /teacher-infos/:id : delete the teacher "id".
     *
     * @param id the id of the teacherInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/teacher-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeacher(@PathVariable String id) {
        log.debug("REST request to delete TeacherInfo : {}", id);
        Optional<UserDTO> userDTO = userService.getTeacherById(id);

        if(userDTO.isPresent() && userService.deleteTeacher(userDTO.get().getEmail())) {
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionFailedAlert(ENTITY_NAME, id)).build();
    }

    @GetMapping("/teacher-infos/find/{query}")
    @Timed
    public ResponseEntity<List<TeacherVM>> find(@PathVariable String query) {
        log.debug("REST request to find teachers by name", query);

        List<User> users = userService.findByAuthoritiesAndName(new Authority(AuthoritiesConstants.TEACHER), query);

        List<TeacherVM> result = users.stream()
            .map(o -> new TeacherVM(new UserDTO(o)))
            .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

}
