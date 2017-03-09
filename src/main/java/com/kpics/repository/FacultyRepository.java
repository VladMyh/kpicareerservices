package com.kpics.repository;

import com.kpics.domain.Faculty;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Faculty entity.
 */
@SuppressWarnings("unused")
public interface FacultyRepository extends MongoRepository<Faculty,String> {

}
