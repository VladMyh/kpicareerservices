package com.kpics.service;

import com.kpics.domain.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Track.
 */
public interface TrackService {

    /**
     * Save a track.
     *
     * @param track the entity to save
     * @return the persisted entity
     */
    Track save(Track track);

    /**
     *  Get all the tracks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Track> findAll(Pageable pageable);

    /**
     *  Get the "id" track.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Track findOne(String id);

    /**
     *  Delete the "id" track.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    /**
     * Get track by name.
     *
     * @param name name of the track.
     * @return     the entity.
     */
    Optional<Track> findByName(String name);
}
