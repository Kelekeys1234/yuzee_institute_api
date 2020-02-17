package com.seeka.app.service;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CourseGradeEligibility;
import com.seeka.app.dao.ICourseGradeEligibilityDAO;

@Service
@Transactional
public class CourseGradeEligibilityService implements ICourseGradeEligibilityService {
	
	@Autowired
	private ICourseGradeEligibilityDAO iCourseGradeEligibilityDAO;
	
	@Override
	public void save(CourseGradeEligibility courseGradeEligibility) {
		iCourseGradeEligibilityDAO.save(courseGradeEligibility);
	}
	
	@Override
	public void update(CourseGradeEligibility courseGradeEligibility) {
		iCourseGradeEligibilityDAO.update(courseGradeEligibility);
	}
	
	@Override
	public CourseGradeEligibility get(String id) {
		return iCourseGradeEligibilityDAO.get(id);
	}
	
	@Override
	public List<CourseGradeEligibility> getAll(){
		return iCourseGradeEligibilityDAO.getAll();
	} 
	
	@Override
	public CourseGradeEligibility getAllEnglishEligibilityByCourse(String courseID) {
		return iCourseGradeEligibilityDAO.getAllEnglishEligibilityByCourse(courseID);
	}
	
}
