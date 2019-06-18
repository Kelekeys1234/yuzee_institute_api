package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CourseDetails;

public interface ICourseDetailsDAO {
	
	public void save(CourseDetails obj);
	public void update(CourseDetails obj);
	public CourseDetails get(BigInteger id);
	public List<CourseDetails> getAll();	
}
