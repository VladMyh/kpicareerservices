package com.kpics.repository;

import com.kpics.domain.StudentInfo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Spring Data MongoDB repository for the StudentInfo entity.
 */
@SuppressWarnings("unused")
public interface StudentInfoRepository extends MongoRepository<StudentInfo,String> {

    Optional<StudentInfo> findOneByUserId(String userId);
}
