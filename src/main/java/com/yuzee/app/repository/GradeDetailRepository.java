package com.yuzee.app.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.bean.GradeDetails;

@Repository
public interface GradeDetailRepository extends MongoRepository<GradeDetails, String> {
	GradeDetails findByCountryNameAndStateNameAndGradeAndEducationSystem(String countryName, String stateName,
			String grade, EducationSystem educationSystem);
}
