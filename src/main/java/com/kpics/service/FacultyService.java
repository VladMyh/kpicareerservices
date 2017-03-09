package com.kpics.service;

import com.kpics.domain.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Faculty.
 */
public interface FacultyService {

    /**
     * Save a faculty.
     *
     * @param faculty the entity to save
     * @return the persisted entity
     */
    Faculty save(Faculty faculty);

    /**
     *  Get all the faculties.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Faculty> findAll(Pageable pageable);

    /**
     *  Get the "id" faculty.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Faculty findOne(String id);

    /**
     *  Delete the "id" faculty.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
