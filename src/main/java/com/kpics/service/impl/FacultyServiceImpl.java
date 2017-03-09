package com.kpics.service.impl;

import com.kpics.service.FacultyService;
import com.kpics.domain.Faculty;
import com.kpics.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Faculty.
 */
@Service
public class FacultyServiceImpl implements FacultyService{

    private final Logger log = LoggerFactory.getLogger(FacultyServiceImpl.class);
    
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    /**
     * Save a faculty.
     *
     * @param faculty the entity to save
     * @return the persisted entity
     */
    @Override
    public Faculty save(Faculty faculty) {
        log.debug("Request to save Faculty : {}", faculty);
        Faculty result = facultyRepository.save(faculty);
        return result;
    }

    /**
     *  Get all the faculties.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Faculty> findAll(Pageable pageable) {
        log.debug("Request to get all Faculties");
        Page<Faculty> result = facultyRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one faculty by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Faculty findOne(String id) {
        log.debug("Request to get Faculty : {}", id);
        Faculty faculty = facultyRepository.findOne(id);
        return faculty;
    }

    /**
     *  Delete the  faculty by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Faculty : {}", id);
        facultyRepository.delete(id);
    }
}
