package com.kpics.repository;

import com.kpics.domain.Idea;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Idea entity.
 */
@SuppressWarnings("unused")
public interface IdeaRepository extends MongoRepository<Idea,String> {

}