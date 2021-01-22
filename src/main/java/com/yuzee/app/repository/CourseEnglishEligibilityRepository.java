package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseEnglishEligibility;

@Repository
public interface CourseEnglishEligibilityRepository extends JpaRepository<CourseEnglishEligibility, String> {

	public List<CourseEnglishEligibility> findByCourseIdAndIsActive(String courseId, Boolean isActive);

	List<CourseEnglishEligibility> findByCourseIdAndIdIn(String courseId, List<String> ids);

	public void deleteByCourseIdAndIdIn(String courseId, List<String> ids);

}