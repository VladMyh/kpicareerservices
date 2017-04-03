package com.kpics.repository;

import com.kpics.domain.Group;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Group entity.
 */
@SuppressWarnings("unused")
public interface GroupRepository extends MongoRepository<Group,String> {

    Optional<Group> findOneByDepartment(String department);

    Optional<Group> findOneByFaculty(String faculty);
}
