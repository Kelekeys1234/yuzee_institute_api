package com.seeka.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.CourseEnglishEligibility;

@Repository
public interface CourseEnglishEligibilityRepository extends JpaRepository<CourseEnglishEligibility, String> {

	public List<CourseEnglishEligibility> findByCourseIdAndIsActive(String courseId, Boolean isActive);
	
	public List<CourseEnglishEligibility> findByCourseId(String courseId);
	
}
