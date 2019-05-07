package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.dao.ICourseEnglishEligibilityDAO;

@Service
@Transactional
public class CourseEnglishEligibilityService implements ICourseEnglishEligibilityService {
	
	@Autowired
	ICourseEnglishEligibilityDAO dao;
	
	@Override
	public void save(CourseEnglishEligibility obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(CourseEnglishEligibility obj) {
		dao.update(obj);
	}
	
	@Override
	public CourseEnglishEligibility get(UUID id) {
		return dao.get(id);
	}
	
	@Override
	public List<CourseEnglishEligibility> getAll(){
		return dao.getAll();
	} 
	
	@Override
	public List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(UUID courseID){
		return dao.getAllEnglishEligibilityByCourse(courseID);
	}
}
