package com.kpics.service;

import com.kpics.domain.StudentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StudentInfo.
 */
public interface StudentInfoService {

    /**
     * Save a studentInfo.
     *
     * @param studentInfo the entity to save
     * @return the persisted entity
     */
    StudentInfo save(StudentInfo studentInfo);

    /**
     *  Get all the studentInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StudentInfo> findAll(Pageable pageable);

    /**
     *  Get the "id" studentInfo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentInfo findOne(String id);

    /**
     *  Delete the "id" studentInfo.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    /**
     * Get studentInfo by user id.
     *
     * @param id the id fu the user.
     * @return   the entity.
     */
    Optional<StudentInfo> findOneByUserId(String id);

    /**
     * Create new student info.
     *
     * @param faculty    Faculty.
     * @param department Department.
     * @param group      Group.
     * @param github     GitHub.
     * @param linkedin   LinkedIn.
     * @param about      About.
     * @param userId     UserId
     * @return           The entity.
     */
    StudentInfo createStudentInfo(String faculty, String department,
                      String group, String github, String linkedin,
                      String about, String userId);
}
