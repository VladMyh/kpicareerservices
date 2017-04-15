package com.kpics.repository;

import com.kpics.domain.TrackDefinition;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the TrackDefinition entity.
 */
@SuppressWarnings("unused")
public interface TrackDefinitionRepository extends MongoRepository<TrackDefinition,String> {

}
