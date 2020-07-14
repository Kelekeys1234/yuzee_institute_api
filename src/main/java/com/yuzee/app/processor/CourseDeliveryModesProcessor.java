package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.bean.CourseDeliveryModes;
import com.yuzee.app.dao.CourseDeliveryModesDao;
import com.yuzee.app.dto.CourseDeliveryModesDto;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class CourseDeliveryModesProcessor {

	@Autowired
	private CourseDeliveryModesDao courseDeliveryModesDao;
	
	public void saveCourseDeliveryModes(CourseDeliveryModes courseDeliveryModes) {
		courseDeliveryModesDao.saveCourseDeliveryModes(courseDeliveryModes);
	}
	
	public List<CourseDeliveryModesDto> getCourseDeliveryModesByCourseId(String courseId) {
		log.debug("Inside getCourseDeliveryModesByCourseId() method");
		List<CourseDeliveryModesDto> courseDeliveryModesResponse = new ArrayList<>();
		log.info("Fetching copurseAdditionalInfo from DB for courseId = "+ courseId);
		List<CourseDeliveryModes> courseDeliveryModesFromDB = courseDeliveryModesDao.getCourseDeliveryModesByCourseId(courseId);
		if(!CollectionUtils.isEmpty(courseDeliveryModesFromDB)) {
			log.info("Additional Info is not null, then start iterating list");
			courseDeliveryModesFromDB.stream().forEach(courseDeliveryMode -> {
				CourseDeliveryModesDto courseDeliveryModesDto = new CourseDeliveryModesDto();
				log.info("Copying Bean class values to DTO class using beanUtils");
				BeanUtils.copyProperties(courseDeliveryMode, courseDeliveryModesDto);
				courseDeliveryModesResponse.add(courseDeliveryModesDto);
			});
		}
		return courseDeliveryModesResponse;
	}
	
}
