package com.kpics.service;

import com.kpics.domain.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Streams.
 */
public interface StreamService {

    /**
     * Save a stream.
     *
     * @param stream the entity to save
     * @return the persisted entity
     */
    Stream save(Stream stream);

    /**
     *  Get all the streams.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Stream> findAll(Pageable pageable);

    /**
     *  Get the "id" stream.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Stream findOne(String id);

    /**
     *  Delete the "id" stream.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
