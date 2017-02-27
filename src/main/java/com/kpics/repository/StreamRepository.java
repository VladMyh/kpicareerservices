package com.kpics.repository;

import com.kpics.domain.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Stream entity.
 */
@SuppressWarnings("unused")
public interface StreamRepository extends MongoRepository<Stream,String> {

    Optional<Stream> findOneByTracksTeacherIds(String id);
}
