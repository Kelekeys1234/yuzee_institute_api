package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseWorkExperienceRequirement;

@Repository
public interface CourseWorkExperienceRequirementRepository
		extends MongoRepository<CourseWorkExperienceRequirement, String> {

}
