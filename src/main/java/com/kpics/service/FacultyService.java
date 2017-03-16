package com.kpics.service;

import com.kpics.domain.Department;
import com.kpics.domain.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Faculty.
 */
public interface FacultyService {

    /**
     * Save or create Faculty.
     *
     * @param faculty The entity to save.
     * @return        The persisted entity.
     */
    Faculty save(Faculty faculty);

    /**
     *  Get all the Faculties.
     *
     *  @param pageable The pagination information.
     *  @return         The list of entities.
     */
    Page<Faculty> findAll(Pageable pageable);

    /**
     *  Get Faculty by id.
     *
     *  @param id The id of the entity.
     *  @return   The entity.
     */
    Faculty findOne(String id);

    /**
     *  Delete Faculty by id.
     *
     *  @param id The id of the entity.
     */
    void delete(String id);

    /**
     * This method adds or updates Department.
     *
     * @param facultyId  Faculty id.
     * @param department Department entity.
     * @return           Updated Faculty object.
     */
    Faculty saveDepartment(String facultyId, Department department);

    /**
     * This method deleted Department from Faculty.
     *
     * @param facultyId    Faculty id.
     * @param departmentId Department id.
     */
    void deleteDepartment(String facultyId, String departmentId);

    /**
     * This method finds department by id.
     *
     * @param facultyId    Id of the Faculty.
     * @param departmentId Id of the Department.
     * @return             Department object.
     */
    Department findDepartment(String facultyId, String departmentId);
}
