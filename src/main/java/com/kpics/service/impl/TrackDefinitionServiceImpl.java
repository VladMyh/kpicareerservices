package com.kpics.service.impl;

import com.kpics.service.TrackDefinitionService;
import com.kpics.domain.TrackDefinition;
import com.kpics.repository.TrackDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing TrackDefinition.
 */
@Service
public class TrackDefinitionServiceImpl implements TrackDefinitionService{

    private final Logger log = LoggerFactory.getLogger(TrackDefinitionServiceImpl.class);
    
    private final TrackDefinitionRepository trackDefinitionRepository;

    public TrackDefinitionServiceImpl(TrackDefinitionRepository trackDefinitionRepository) {
        this.trackDefinitionRepository = trackDefinitionRepository;
    }

    /**
     * Save a trackDefinition.
     *
     * @param trackDefinition the entity to save
     * @return the persisted entity
     */
    @Override
    public TrackDefinition save(TrackDefinition trackDefinition) {
        log.debug("Request to save TrackDefinition : {}", trackDefinition);
        TrackDefinition result = trackDefinitionRepository.save(trackDefinition);
        return result;
    }

    /**
     *  Get all the trackDefinitions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<TrackDefinition> findAll(Pageable pageable) {
        log.debug("Request to get all TrackDefinitions");
        Page<TrackDefinition> result = trackDefinitionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one trackDefinition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public TrackDefinition findOne(String id) {
        log.debug("Request to get TrackDefinition : {}", id);
        TrackDefinition trackDefinition = trackDefinitionRepository.findOne(id);
        return trackDefinition;
    }

    /**
     *  Delete the  trackDefinition by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete TrackDefinition : {}", id);
        trackDefinitionRepository.delete(id);
    }
}
