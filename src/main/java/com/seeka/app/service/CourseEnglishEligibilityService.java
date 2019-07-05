package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.dao.ICourseEnglishEligibilityDAO;

@Service
@Transactional
public class CourseEnglishEligibilityService implements ICourseEnglishEligibilityService {
	
	@Autowired
	private ICourseEnglishEligibilityDAO iCourseEnglishEligibilityDAO;
	
	@Override
	public void save(CourseEnglishEligibility obj) {
		iCourseEnglishEligibilityDAO.save(obj);
	}
	
	@Override
	public void update(CourseEnglishEligibility obj) {
		iCourseEnglishEligibilityDAO.update(obj);
	}
	
	@Override
	public CourseEnglishEligibility get(BigInteger id) {
		return iCourseEnglishEligibilityDAO.get(id);
	}
	
	@Override
	public List<CourseEnglishEligibility> getAll(){
		return iCourseEnglishEligibilityDAO.getAll();
	} 
	
	@Override
	public List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(BigInteger courseID){
		return iCourseEnglishEligibilityDAO.getAllEnglishEligibilityByCourse(courseID);
	}
}
