package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Top10Course;

public interface ITop10CourseDAO {

	void save(final Top10Course top10Course);

	void deleteAll();

	List<String> getAllDistinctFaculty();

	List<Top10Course> getTop10CourseKeyword(String faculty);
}
