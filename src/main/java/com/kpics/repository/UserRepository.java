package com.kpics.repository;

import com.kpics.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the User entity.
 */
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmail(String email);

    @Query("{$and: [{'authorities': {'_id': ?0}}, " +
        "{$or : [{'first_name': {'$regex': ?1, '$options' : 'i'}}, {'last_name': {'$regex': ?1, '$options' : 'i'}}]}]}")
    List<User> findByAuthoritiesAndFirstNameOrLastNameLike(String authority, String query);

}
