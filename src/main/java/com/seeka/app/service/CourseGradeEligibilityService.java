package com.seeka.app.service;import java.util.List;
import java.util.Optional;

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
		Optional<CourseGradeEligibility> courseGradeEligibility = iCourseGradeEligibilityDAO.get(id);
		if (courseGradeEligibility.isPresent()) {
			return courseGradeEligibility.get();
		} else  {
			return null;
		}
	}
	
	@Override
	public List<CourseGradeEligibility> getAll(){
		return iCourseGradeEligibilityDAO.getAll();
	} 
	
	@Override
	public CourseGradeEligibility getCourseGradeEligibilityByCourseId(String courseId) {
		return iCourseGradeEligibilityDAO.getCourseGradeEligibilityByCourseId(courseId);
	}
	
}
