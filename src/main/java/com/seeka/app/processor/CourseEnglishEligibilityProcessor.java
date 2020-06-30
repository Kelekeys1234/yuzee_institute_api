package com.seeka.app.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.seeka.app.bean.CourseEnglishEligibility;
import com.seeka.app.dao.CourseEnglishEligibilityDao;
import com.seeka.app.dto.CourseEnglishEligibilityDto;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class CourseEnglishEligibilityProcessor {

	@Autowired
	private CourseEnglishEligibilityDao iCourseEnglishEligibilityDAO;
	
	public void save(CourseEnglishEligibility obj) {
		iCourseEnglishEligibilityDAO.save(obj);
	}
	
	public void update(CourseEnglishEligibility obj) {
		iCourseEnglishEligibilityDAO.update(obj);
	}
	
	public CourseEnglishEligibility get(String id) {
		return iCourseEnglishEligibilityDAO.get(id);
	}
	
	public List<CourseEnglishEligibility> getAll(){
		return iCourseEnglishEligibilityDAO.getAll();
	} 
	
	public List<CourseEnglishEligibilityDto> getAllEnglishEligibilityByCourse(String courseId){
		log.debug("Inside getAllEnglishEligibilityByCourse() method");
		List<CourseEnglishEligibilityDto> courseEnglishEligibilityResponse = new ArrayList<>();
		log.info("Fetching englishEligibilties from DB for courseId = "+ courseId);
		List<CourseEnglishEligibility> courseEnglishEligibilitiesFromDB = iCourseEnglishEligibilityDAO.getAllEnglishEligibilityByCourse(courseId);
		if(!CollectionUtils.isEmpty(courseEnglishEligibilitiesFromDB)) {
			log.info("English Eligibilities coming from DB, start iterating data");
			courseEnglishEligibilitiesFromDB.stream().forEach(courseEnglishEligibility -> {
				CourseEnglishEligibilityDto courseEnglishEligibilityDto = new CourseEnglishEligibilityDto();
				BeanUtils.copyProperties(courseEnglishEligibility, courseEnglishEligibilityDto);
				courseEnglishEligibilityResponse.add(courseEnglishEligibilityDto);
			});
		}
		return courseEnglishEligibilityResponse;
	}

}
