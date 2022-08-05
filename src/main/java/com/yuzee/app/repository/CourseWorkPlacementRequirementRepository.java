package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseWorkPlacementRequirement;

@Repository
public interface CourseWorkPlacementRequirementRepository
		extends MongoRepository<CourseWorkPlacementRequirement, String> {

}
