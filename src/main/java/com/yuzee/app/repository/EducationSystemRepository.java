package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.EducationSystem;

@Repository
public interface EducationSystemRepository extends MongoRepository<EducationSystem, String> {

	EducationSystem findByNameAndCountryNameAndStateName(String name, String countryName, String stateName);
}
