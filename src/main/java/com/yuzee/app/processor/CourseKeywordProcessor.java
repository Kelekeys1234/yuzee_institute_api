package com.yuzee.app.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.CourseKeywords;
import com.yuzee.app.dao.CourseKeywordDao;

@Service
@Transactional
public class CourseKeywordProcessor {

	@Autowired
	private CourseKeywordDao iCourseKeywordDAO;

	public void save(CourseKeywords courseKeywords) {
		iCourseKeywordDAO.save(courseKeywords);
	}

	public void update(CourseKeywords courseKeywords) {
		iCourseKeywordDAO.update(courseKeywords);
	}

	public List<CourseKeywords> getAll() {
		return iCourseKeywordDAO.getAll();
	}

	public List<CourseKeywords> searchCourseKeyword(String keyword) {
		return iCourseKeywordDAO.searchCourseKeyword(keyword);
	}
}
