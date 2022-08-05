package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Faculty;

@Repository
public interface FacultyRepository extends MongoRepository<Faculty, String> {

	Faculty findByNameIgnoreCase(String name);

}
