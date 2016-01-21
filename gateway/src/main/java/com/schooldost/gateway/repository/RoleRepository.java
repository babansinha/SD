package com.schooldost.gateway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.schooldost.gateway.mongo.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

}
