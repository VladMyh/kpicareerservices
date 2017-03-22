package com.kpics.service;

import com.kpics.domain.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Ideas.
 */
public interface IdeaService {

    /**
     * Save a idea.
     *
     * @param idea the entity to save
     * @return the persisted entity
     */
    Idea save(Idea idea);

    /**
     * Get all the ideas
     *
     * @param pageable the pagination information
     * @return the list of all ideas
     */
    Page<Idea> findAll(Pageable pageable);

    /**
     * Get all ideas by created company's
     *
     * @param id company id
     * @return the list of entities
     */
    Optional<Idea> findByCompanyId(String id);

    /**
     * Get all ideas by tag id
     *
     * @param id tag id
     * @return the list of entities
     */
    Optional<Idea> findByTagId(String id);

    /**
     *  Get the "id" idea.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Idea findOne(String id);

    /**
     *  Delete the "id" idea.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
