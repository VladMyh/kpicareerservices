package com.kpics.service;

import com.kpics.domain.TeacherInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TeacherInfo.
 */
public interface TeacherInfoService {

    /**
     * Save a teacherInfo.
     *
     * @param teacherInfo the entity to save
     * @return the persisted entity
     */
    TeacherInfo save(TeacherInfo teacherInfo);

    /**
     *  Get all the teacherInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TeacherInfo> findAll(Pageable pageable);

    /**
     *  Get the "id" teacherInfo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TeacherInfo findOne(String id);

    /**
     *  Delete the "id" teacherInfo.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    /**
     * Create new teacher info.
     *
     * @param faculty    Faculty.
     * @param department Department.
     * @param about      About.
     * @return           The entity.
     */
    TeacherInfo createTeacherInfo(String faculty, String department, String about, String userId);
}
