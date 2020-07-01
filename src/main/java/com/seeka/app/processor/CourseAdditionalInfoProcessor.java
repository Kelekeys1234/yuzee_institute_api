package com.seeka.app.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.seeka.app.bean.CourseDeliveryModes;
import com.seeka.app.dao.CourseAdditionalInfoDao;
import com.seeka.app.dto.CourseDeliveryModesDto;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class CourseAdditionalInfoProcessor {

	@Autowired
	private CourseAdditionalInfoDao courseAdditionalInfoDao;
	
	public void saveCourseAdditionalInfo(CourseDeliveryModes courseAdditionalInfo) {
		courseAdditionalInfoDao.saveCourseAdditionalInfo(courseAdditionalInfo);
	}
	
	public List<CourseDeliveryModesDto> getCourseAdditionalInfoByCourseId(String courseId) {
		log.debug("Inside getCourseAdditionalInfoByCourseId() method");
		List<CourseDeliveryModesDto> courseAdditionalInfoResponse = new ArrayList<>();
		log.info("Fetching copurseAdditionalInfo from DB for courseId = "+ courseId);
		List<CourseDeliveryModes> courseAdditionalInfosFromDB = courseAdditionalInfoDao.getCourseAdditionalInfoByCourseId(courseId);
		if(!CollectionUtils.isEmpty(courseAdditionalInfosFromDB)) {
			log.info("Additional Info is not null, then start iterating list");
			courseAdditionalInfosFromDB.stream().forEach(courseAdditionalInfo -> {
				CourseDeliveryModesDto additionalInfoDto = new CourseDeliveryModesDto();
				log.info("Copying Bean class values to DTO class using beanUtils");
				BeanUtils.copyProperties(courseAdditionalInfo, additionalInfoDto);
				courseAdditionalInfoResponse.add(additionalInfoDto);
			});
		}
		return courseAdditionalInfoResponse;
	}
	
}
