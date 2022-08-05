package com.yuzee.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.yuzee.app.bean.CourseMinRequirement;

public interface CourseMinimumRequirementsRepository extends MongoRepository<CourseMinRequirement, String> {

	Page<CourseMinRequirement> findByCourseId(String courseId, Pageable pageable);

	CourseMinRequirement findByCourseIdAndId(String courseId, String id);

	long deleteByCourseIdAndId(String courseId, String id);
}
