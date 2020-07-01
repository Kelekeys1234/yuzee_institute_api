package com.seeka.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CourseMinRequirement;

@Repository
public interface CourseMinimumRequirementsRepository extends JpaRepository<CourseMinRequirement, String> {

	public List<CourseMinRequirement> findByCourseId(String courseId);
	
}
