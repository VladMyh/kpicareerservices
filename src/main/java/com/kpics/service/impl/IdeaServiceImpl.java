package com.kpics.service.impl;

import com.kpics.service.IdeaService;
import com.kpics.domain.Idea;
import com.kpics.repository.IdeaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Idea.
 */
@Service
public class IdeaServiceImpl implements IdeaService{

    private final Logger log = LoggerFactory.getLogger(IdeaServiceImpl.class);
    
    private final IdeaRepository ideaRepository;

    public IdeaServiceImpl(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    /**
     * Save a idea.
     *
     * @param idea the entity to save
     * @return the persisted entity
     */
    @Override
    public Idea save(Idea idea) {
        log.debug("Request to save Idea : {}", idea);
        Idea result = ideaRepository.save(idea);
        return result;
    }

    /**
     *  Get all the ideas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Idea> findAll(Pageable pageable) {
        log.debug("Request to get all Ideas");
        Page<Idea> result = ideaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one idea by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Idea findOne(String id) {
        log.debug("Request to get Idea : {}", id);
        Idea idea = ideaRepository.findOne(id);
        return idea;
    }

    /**
     *  Delete the  idea by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Idea : {}", id);
        ideaRepository.delete(id);
    }
}
