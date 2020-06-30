package com.seeka.app.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.seeka.app.bean.CourseAdditionalInfo;
import com.seeka.app.dao.CourseAdditionalInfoDao;
import com.seeka.app.dto.CourseAdditionalInfoDto;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class CourseAdditionalInfoProcessor {

	@Autowired
	private CourseAdditionalInfoDao courseAdditionalInfoDao;
	
	public void saveCourseAdditionalInfo(CourseAdditionalInfo courseAdditionalInfo) {
		courseAdditionalInfoDao.saveCourseAdditionalInfo(courseAdditionalInfo);
	}
	
	public List<CourseAdditionalInfoDto> getCourseAdditionalInfoByCourseId(String courseId) {
		log.debug("Inside getCourseAdditionalInfoByCourseId() method");
		List<CourseAdditionalInfoDto> courseAdditionalInfoResponse = new ArrayList<>();
		log.info("Fetching copurseAdditionalInfo from DB for courseId = "+ courseId);
		List<CourseAdditionalInfo> courseAdditionalInfosFromDB = courseAdditionalInfoDao.getCourseAdditionalInfoByCourseId(courseId);
		if(!CollectionUtils.isEmpty(courseAdditionalInfosFromDB)) {
			log.info("Additional Info is not null, then start iterating list");
			courseAdditionalInfosFromDB.stream().forEach(courseAdditionalInfo -> {
				CourseAdditionalInfoDto additionalInfoDto = new CourseAdditionalInfoDto();
				log.info("Copying Bean class values to DTO class using beanUtils");
				BeanUtils.copyProperties(courseAdditionalInfo, additionalInfoDto);
				courseAdditionalInfoResponse.add(additionalInfoDto);
			});
		}
		return courseAdditionalInfoResponse;
	}
	
}
