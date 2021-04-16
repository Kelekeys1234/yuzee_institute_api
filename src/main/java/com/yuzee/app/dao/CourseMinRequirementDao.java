package com.yuzee.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.CourseMinRequirement;
import com.yuzee.app.exception.ValidationException;

public interface CourseMinRequirementDao {

	CourseMinRequirement save(final CourseMinRequirement courseMinRequirement) throws ValidationException;

	Page<CourseMinRequirement> findByCourseId(final String courseId, Pageable pageable);

	CourseMinRequirement findByCourseIdAndId(String courseId, String id);

	long deleteByCourseIdAndId(String courseId, String id);
}
