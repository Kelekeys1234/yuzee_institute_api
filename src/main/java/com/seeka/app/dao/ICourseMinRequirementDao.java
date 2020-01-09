package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseMinRequirement;

public interface ICourseMinRequirementDao{

    public void save(final CourseMinRequirement obj);
    public List<CourseMinRequirement> get(final BigInteger id);
}
