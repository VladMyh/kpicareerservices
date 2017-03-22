package com.kpics.repository;

import com.kpics.domain.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by acube on 23.03.2017.
 */
public interface IdeaRepository extends MongoRepository<Idea, String> {

    Optional<Idea> findById(String id);

    Page<Idea> findByCompanyId(String id);

    Page<Idea> findByTagId(String id);
}
