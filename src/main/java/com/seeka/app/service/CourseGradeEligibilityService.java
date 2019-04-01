package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CourseGradeEligibility;
import com.seeka.app.dao.ICourseGradeEligibilityDAO;

@Service
@Transactional
public class CourseGradeEligibilityService implements ICourseGradeEligibilityService {
	
	@Autowired
	ICourseGradeEligibilityDAO dao;
	
	@Override
	public void save(CourseGradeEligibility obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(CourseGradeEligibility obj) {
		dao.update(obj);
	}
	
	@Override
	public CourseGradeEligibility get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public List<CourseGradeEligibility> getAll(){
		return dao.getAll();
	} 
	
}
