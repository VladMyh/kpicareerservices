package com.kpics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kpics.domain.Skill;
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
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing StudentInfo.
 */
@RestController
@RequestMapping("/api")
public class StudentInfoResource {

    private final Logger log = LoggerFactory.getLogger(StudentInfoResource.class);

    private static final String ENTITY_NAME = "studentInfo";

    private final UserService userService;

    public StudentInfoResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * PUT  /student-infos : Updates an existing studentInfo.
     *
     * @param userDTO the studentInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentInfo,
     * or with status 400 (Bad Request) if the studentInfo is not valid,
     * or with status 500 (Internal Server Error) if the studentInfo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-infos")
    @Timed
    public ResponseEntity<UserDTO> updateStudent(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to update Student : {}", userDTO);

        Optional<UserDTO> result = userService.getStudentById(userDTO.getId());
        result.ifPresent(u -> userService.updateUser(userDTO));

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.orElse(null).getId()))
            .body(result.get());
    }

    /**
     * GET  /student-infos : get all the students.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentInfos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/student-infos")
    @Timed
    public ResponseEntity<List<StudentVM>> getAllStudent(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Students");
        Page<UserDTO> page = userService.getAllStudents(pageable);
        List<StudentVM> result = page.getContent().stream()
            .map(StudentVM::new)
            .collect(Collectors.toList());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-infos");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    /**
     * GET  /student-infos/:id : get the student by "id".
     *
     * @param id the id of the student to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentInfo, or with status 404 (Not Found)
     */
    @GetMapping("/student-infos/{id}")
    @Timed
    public ResponseEntity<StudentVM> getStudent(@PathVariable String id) {
        log.debug("REST request to get Student by id : {}", id);
        Optional<UserDTO> userDTO = userService.getStudentById(id);
        StudentVM studentVM = null;

        if(userDTO.isPresent()) {
            studentVM = new StudentVM(userDTO.get());
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
        Optional<UserDTO> userDTO = userService.getStudentById(id);
        userDTO.ifPresent(u -> userService.deleteUser(u.getEmail()));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    @PutMapping("/student-infos/{id}/addSkill")
    @Timed
    public ResponseEntity<StudentVM> addSkillToStudent(@PathVariable String id, @Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("Rest request to add Skill to Student : {}", id, skill);
        Optional<UserDTO> userDTO = userService.getStudentById(id);

        if(userDTO.isPresent()){
            StudentVM studentVM = null;

            for(Skill studentSkill : userDTO.get().getStudentInfo().getSkills()) {
                if (studentSkill.equals(skill)){
                    return ResponseEntity.badRequest()
                        .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "alreadyexist", "Skill already exists"))
                        .body(null);
                }
            }


            userDTO.get().getStudentInfo().getSkills().add(skill);
            userService.updateUser(userDTO.get());

            studentVM = new StudentVM(userDTO.get());

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDTO.get().getId()))
                .body(studentVM);
        }

        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "iddoesntexist", "Student with giver id doesn't exists"))
            .body(null);
    }

    @PutMapping("/student-infos/{id}/updateSkill")
    @Timed
    public ResponseEntity<StudentVM> updateSkill(@PathVariable String id, @Valid @RequestBody Skill[] skills) throws URISyntaxException {
        log.debug("Rest request to update Skill for Student : {}", id, skills);
        Optional<UserDTO> userDTO = userService.getStudentById(id);

        if (skills.length != 2){
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "skillshavetobetwo", "Skills array doesn't contain two skills"))
                .body(null);
        }


        if(userDTO.isPresent()){
            StudentVM studentVM = null;

            Skill oldSkill = skills[0];
            Skill newSkill = skills[1];

            Set<Skill> skillsSet = userDTO.get().getStudentInfo().getSkills();

            if (skillsSet.contains(oldSkill)){
                skillsSet.remove(oldSkill);
                skillsSet.add(newSkill);

                userService.updateUser(userDTO.get());
                studentVM = new StudentVM(userDTO.get());

                return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDTO.get().getId()))
                    .body(studentVM);
            }

            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "skilldoesntexist", "Student skill doesn't exists"))
                .body(null);
        }

        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "iddoesntexist", "Student with giver id doesn't exists"))
            .body(null);
    }

    @PutMapping("/student-infos/{id}/deleteSkill")
    @Timed
    public ResponseEntity<StudentVM> deleteSkill(@PathVariable String id, @Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("Rest request to delete Skill from Student : {}", id, skill);
        Optional<UserDTO> userDTO = userService.getStudentById(id);

        if(userDTO.isPresent()){
            StudentVM studentVM = null;

            for(Skill studentSkill : userDTO.get().getStudentInfo().getSkills()) {
                if (studentSkill.equals(skill)){
                    userDTO.get().getStudentInfo().getSkills().remove(studentSkill);
                    userService.updateUser(userDTO.get());
                    studentVM = new StudentVM(userDTO.get());

                    return ResponseEntity.ok()
                        .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDTO.get().getId()))
                        .body(studentVM);
                }
            }

            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "skilldoesntexist", "Student skill doesn't exists"))
                .body(null);
        }

        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "iddoesntexist", "Student with giver id doesn't exists"))
            .body(null);
    }

}
