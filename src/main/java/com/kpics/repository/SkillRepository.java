package com.kpics.repository;


import com.kpics.domain.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
@SuppressWarnings("unused")
public interface SkillRepository extends MongoRepository<Skill, String> {

}
