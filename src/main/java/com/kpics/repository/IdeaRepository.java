package com.kpics.repository;

import com.kpics.domain.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Idea entity.
 */
@SuppressWarnings("unused")
public interface IdeaRepository extends MongoRepository<Idea, String> {

    Optional<Idea> findById(String id);

    Optional<Idea> findByCompanyId(String id);

    Optional<Idea> findByTags(String id);
}
