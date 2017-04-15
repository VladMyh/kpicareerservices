package com.kpics.service;

import com.kpics.domain.TrackDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing TrackDefinition.
 */
public interface TrackDefinitionService {

    /**
     * Save a trackDefinition.
     *
     * @param trackDefinition the entity to save
     * @return the persisted entity
     */
    TrackDefinition save(TrackDefinition trackDefinition);

    /**
     *  Get all the trackDefinitions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TrackDefinition> findAll(Pageable pageable);

    /**
     *  Get the "id" trackDefinition.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TrackDefinition findOne(String id);

    /**
     *  Delete the "id" trackDefinition.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
