package com.kpics.service.impl;

import com.kpics.domain.Idea;
import com.kpics.repository.IdeaRepository;
import com.kpics.service.IdeaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by acube on 23.03.2017.
 */
public class IdeaServiceImpl implements IdeaService {

    private final Logger log = LoggerFactory.getLogger(StreamServiceImpl.class);

    private final IdeaRepository ideaRepository;

    public IdeaServiceImpl(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    @Override
    public Idea save(Idea idea) {
        log.debug("Request to save Idea : {}", idea);
        return ideaRepository.save(idea);
    }

    @Override
    public Page<Idea> findAll(Pageable pageable) {
        log.debug("Request to get all Ideas");
        return ideaRepository.findAll(pageable);
    }

    @Override
    public Page<Idea> findByCompanyId(String id) {
        log.debug("Request to get Ideas by company id : {}", id);
        return ideaRepository.findByCompanyId(id);
    }

    @Override
    public Page<Idea> findByTagId(String id) {
        log.debug("Request to get Ideas by tag id : {}", id);
        return ideaRepository.findByTagId(id);
    }

    @Override
    public Idea findOne(String id) {
        log.debug("Request to get idea by id : {}", id);
        return ideaRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete idea by id : {}", id);
        ideaRepository.delete(id);
    }
}
