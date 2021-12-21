package com.treinetic.assignment.repository;

import com.treinetic.assignment.entity.Role;
import com.treinetic.assignment.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    @Query("{'email': ?0}")
    User findByUsername(String username);

}
