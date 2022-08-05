package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseEnglishEligibility;

@Repository
public interface CourseEnglishEligibilityRepository extends MongoRepository<CourseEnglishEligibility, String> {

	public List<CourseEnglishEligibility> findByCourseId(String courseId);
}