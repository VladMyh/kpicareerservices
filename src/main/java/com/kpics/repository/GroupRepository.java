package com.kpics.repository;

import com.kpics.domain.Group;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Group entity.
 */
@SuppressWarnings("unused")
public interface GroupRepository extends MongoRepository<Group,String> {

}
