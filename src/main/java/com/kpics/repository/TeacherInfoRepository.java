package com.kpics.repository;

import com.kpics.domain.TeacherInfo;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the TeacherInfo entity.
 */
@SuppressWarnings("unused")
public interface TeacherInfoRepository extends MongoRepository<TeacherInfo,String> {

}
