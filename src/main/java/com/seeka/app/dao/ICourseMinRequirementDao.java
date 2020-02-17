package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.CourseMinRequirement;

public interface ICourseMinRequirementDao {

	void save(final CourseMinRequirement obj);

	List<CourseMinRequirement> get(final String id);
}
