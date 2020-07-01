package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.CourseMinRequirement;

public interface CourseMinRequirementDao {

	public void save(final CourseMinRequirement obj);

	public List<CourseMinRequirement> getCourseMinRequirementByCourseId(final String id);
}
