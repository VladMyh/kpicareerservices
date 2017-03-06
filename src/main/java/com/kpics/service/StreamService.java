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

    /**
     * Delete track from stream.
     *
     * @param streamId Stream id.
     * @param trackId  Track id.
     * @return         True if track is deleted, false otherwise.
     */
    boolean deleteTrack(String streamId, String trackId);

    /**
     * Delete subject from track.
     *
     * @param streamId  Stream id.
     * @param trackId   Track id.
     * @param subjectId Subject id.
     * @return          True if subject is deleted, false otherwise.
     */
    boolean deleteSubject(String streamId, String trackId, String subjectId);

    /**
     * Delete teacher from track.
     *
     * @param streamId  Stream id.
     * @param trackId   Track id.
     * @param teacherId Teacher id.
     * @return          True if teacher is deleted, false otherwise.
     */
    boolean deleteTeacher(String streamId, String trackId, String teacherId);
}
