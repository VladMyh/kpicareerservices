package com.kpics.service;

import com.kpics.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Group.
 */
public interface GroupService {

    /**
     * Save a group.
     *
     * @param group the entity to save.
     * @return      the persisted entity.
     */
    Group save(Group group);

    /**
     *  Get all the groups.
     *
     *  @param pageable the pagination information.
     *  @return         the list of entities.
     */
    Page<Group> findAll(Pageable pageable);

    /**
     *  Get the "id" group.
     *
     *  @param id the id of the entity.
     *  @return   the entity.
     */
    Group findOne(String id);

    /**
     *  Delete the "id" group.
     *
     *  @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Find groups by faculty.
     *
     * @param faculty faculty.
     * @return        the list of entities.
     */
    List<Group> findByFaculty(String faculty);

    /**
     * Find groups by faculty and department.
     *
     * @param faculty    faculty.
     * @param department department.
     * @return           the list of entities.
     */
    List<Group> findByFacultyAndDepartment(String faculty, String department);
}
