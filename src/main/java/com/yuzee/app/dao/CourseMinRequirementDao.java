package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseMinRequirement;

public interface CourseMinRequirementDao {

	public void save(final CourseMinRequirement obj);

	public List<CourseMinRequirement> getCourseMinRequirementByCourseId(final String id);
}
