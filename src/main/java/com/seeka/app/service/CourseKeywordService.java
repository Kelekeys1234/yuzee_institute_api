package com.seeka.app.service;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CourseKeywords;
import com.seeka.app.dao.ICourseKeywordDAO;

@Service
@Transactional
public class CourseKeywordService implements ICourseKeywordService {
	
	@Autowired
	private ICourseKeywordDAO iCourseKeywordDAO;
	
	@Override
	public void save(CourseKeywords courseKeywords) {
		iCourseKeywordDAO.save(courseKeywords);
	}
	
	@Override
	public void update(CourseKeywords courseKeywords) {
		iCourseKeywordDAO.update(courseKeywords);
	}
	
	@Override
	public List<CourseKeywords> getAll(){
		return iCourseKeywordDAO.getAll();
	}
	
	@Override
	public List<CourseKeywords> searchCourseKeyword(String keyword){
		return iCourseKeywordDAO.searchCourseKeyword(keyword);
	}
}
