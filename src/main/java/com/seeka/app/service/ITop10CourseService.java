package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.Top10Course;

public interface ITop10CourseService {

	void saveTop10Courses(Top10Course top10Course);
	
	void deleteAllTop10Courses();

	List<String> getAllDistinctFaculty();	
}
