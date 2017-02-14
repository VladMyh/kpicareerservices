package com.kpics.repository;

import com.kpics.domain.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Stream entity.
 */
@SuppressWarnings("unused")
public interface StreamRepository extends MongoRepository<Stream,String> {

}
