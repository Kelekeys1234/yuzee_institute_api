package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseEnglishEligibility;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseEnglishEligibilityDao;
import com.yuzee.app.dto.CourseEnglishEligibilityDto;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.RuntimeNotFoundException;
import com.yuzee.app.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseEnglishEligibilityProcessor {

	@Autowired
	CourseDao courseDao;

	@Autowired
	private CourseEnglishEligibilityDao courseEnglishEligibilityDAO;

	public List<CourseEnglishEligibilityDto> getAllEnglishEligibilityByCourse(String courseId) {
		log.debug("Inside getAllEnglishEligibilityByCourse() method");
		List<CourseEnglishEligibilityDto> courseEnglishEligibilityResponse = new ArrayList<>();
		log.info("Fetching englishEligibilties from DB for courseId = " + courseId);
		List<CourseEnglishEligibility> courseEnglishEligibilitiesFromDB = courseEnglishEligibilityDAO
				.getAllEnglishEligibilityByCourse(courseId);
		if (!CollectionUtils.isEmpty(courseEnglishEligibilitiesFromDB)) {
			log.info("English Eligibilities coming from DB, start iterating data");
			courseEnglishEligibilitiesFromDB.stream().forEach(courseEnglishEligibility -> {
				CourseEnglishEligibilityDto courseEnglishEligibilityDto = new CourseEnglishEligibilityDto();
				BeanUtils.copyProperties(courseEnglishEligibility, courseEnglishEligibilityDto);
				courseEnglishEligibilityResponse.add(courseEnglishEligibilityDto);
			});
		}
		return courseEnglishEligibilityResponse;
	}

	public void saveUpdateCourseEnglishEligibilities(String userId, String courseId,
			@Valid List<CourseEnglishEligibilityDto> courseEnglishEligibilityDtos)
			throws NotFoundException, ValidationException {
		log.info("inside CourseEnglishEligibilityDao.saveUpdateCourseEnglishEligibilities");
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {

			log.info("getting the ids of entitities to be updated");
			Set<String> updateRequestIds = courseEnglishEligibilityDtos.stream()
					.filter(e -> !StringUtils.isEmpty(e.getId())).map(CourseEnglishEligibilityDto::getId)
					.collect(Collectors.toSet());

			log.info("verfiy if ids exists against course");
			Map<String, CourseEnglishEligibility> existingCourseEnglishEligibilitysMap = courseEnglishEligibilityDAO
					.findByCourseIdAndIdIn(courseId, updateRequestIds.stream().collect(Collectors.toList())).stream()
					.collect(Collectors.toMap(CourseEnglishEligibility::getId, e -> e));

			List<CourseEnglishEligibility> courseEnglishEligibilitys = new ArrayList<>();

			log.info("loop the requested list to collect the entitities to be saved/updated");
			courseEnglishEligibilityDtos.stream().forEach(e -> {
				CourseEnglishEligibility courseEnglishEligibility = new CourseEnglishEligibility();
				if (!StringUtils.isEmpty(e.getId())) {
					log.info("entityId is present so going to see if it is present in db if yes then we have to update it");
					courseEnglishEligibility = existingCourseEnglishEligibilitysMap.get(e.getId());
					if (courseEnglishEligibility == null) {
						log.error("invalid course english eligbility id : {}", e.getId());
						throw new RuntimeNotFoundException("invalid course english eligbility id : " + e.getId());
					}
				}
				BeanUtils.copyProperties(e, courseEnglishEligibility);
				courseEnglishEligibility.setCourse(course);
				courseEnglishEligibility.setAuditFields(userId);
				courseEnglishEligibilitys.add(courseEnglishEligibility);
			});
			courseEnglishEligibilityDAO.saveAll(courseEnglishEligibilitys);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	public void deleteByCourseEnglishEligibilityIds(String userId, String courseId, List<String> englishEligibilityIds)
			throws NotFoundException, ForbiddenException {
		log.info("inside CourseEnglishEligibilityDao.deleteByCourseEnglishEligibilityIds");
		List<CourseEnglishEligibility> courseEnglishEligibilitys = courseEnglishEligibilityDAO
				.findByCourseIdAndIdIn(courseId, englishEligibilityIds);
		if (englishEligibilityIds.size() == courseEnglishEligibilitys.size()) {
			if (courseEnglishEligibilitys.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more english eligbilities");
				throw new ForbiddenException("no access to delete one more english eligbilities");
			}
			courseEnglishEligibilityDAO.deleteByCourseIdAndIdIn(courseId, englishEligibilityIds);
		} else {
			log.error("one or more invalid course_english eligbility_ids");
			throw new NotFoundException("one or more invalid course_english eligbility_ids");
		}
	}
}
