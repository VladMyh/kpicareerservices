package com.kpics.repository;

import com.kpics.domain.Track;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Track entity.
 */
@SuppressWarnings("unused")
public interface TrackRepository extends MongoRepository<Track,String> {

    Optional<Track> findOneByName(String name);
}
