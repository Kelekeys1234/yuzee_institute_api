package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseVaccineRequirement;

@Repository
public interface CourseVaccineRequirementRepository extends MongoRepository<CourseVaccineRequirement, String> {

}
