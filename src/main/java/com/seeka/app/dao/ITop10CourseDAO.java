package com.seeka.app.dao;

import com.seeka.app.bean.Top10Course;

public interface ITop10CourseDAO {

	void save(final Top10Course top10Course);
	void deleteAll();
}
