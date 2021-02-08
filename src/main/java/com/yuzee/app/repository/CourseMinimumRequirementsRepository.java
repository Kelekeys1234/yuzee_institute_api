package com.yuzee.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseMinRequirement;

public interface CourseMinimumRequirementsRepository extends JpaRepository<CourseMinRequirement, String> {

	Page<CourseMinRequirement> findByCourseId(String courseId, Pageable pageable);

	CourseMinRequirement findByCourseIdAndId(String courseId, String id);

	void deleteByCourseIdAndId(String courseId, String id);
}
