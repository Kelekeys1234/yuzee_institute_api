package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.CourseMinRequirement;

public interface ICourseMinRequirementDao {

	void save(final CourseMinRequirement obj);

	List<CourseMinRequirement> get(final BigInteger id);
}
