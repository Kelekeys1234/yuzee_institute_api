package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseContactPerson;
import com.yuzee.app.dao.CourseContactPersonDao;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.CourseContactPersonDto;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseContactPersonProcessor {

	@Autowired
	CourseContactPersonDao courseFundingDao;

	@Autowired
	InstituteDao instituteDao;

	@Autowired
	CourseDao courseDao;

	@Autowired
	private CommonProcessor commonProcessor;

	public void saveCourseContactPersons(String userId, String courseId,
			@Valid List<CourseContactPersonDto> courseFundingDtos) throws NotFoundException, ValidationException {
		log.info("inside CourseContactPersonProcessor.saveCourseContactPersons");
		Course course = validateAndGetCourseById(courseId);
		log.info("going to see if user ids are valid");
		commonProcessor.validateAndGetUsersByUserIds(
				courseFundingDtos.stream().map(CourseContactPersonDto::getUserId).collect(Collectors.toList()));
		List<CourseContactPerson> courseContactPersons = new ArrayList<>();
		courseFundingDtos.stream().forEach(e -> {
			CourseContactPerson courseContactPerson = new CourseContactPerson();
			courseContactPerson.setAuditFields(userId);
			courseContactPerson.setCourse(course);
			courseContactPerson.setUserId(e.getUserId());
			courseContactPersons.add(courseContactPerson);
		});
		courseFundingDao.saveAll(courseContactPersons);
	}

	public void deleteCourseContactPersonsByUserIds(String userId, String courseId, List<String> userIds)
			throws NotFoundException, ForbiddenException {
		log.info("inside CourseContactPersonProcessor.deleteCourseContactPersonsByUserIds");
		validateAndGetCourseById(courseId);
		List<CourseContactPerson> courseFundings = courseFundingDao.findByCourseIdAndUserIdIn(courseId, userIds);
		if (userIds.size() == courseFundings.size()) {
			if (courseFundings.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more fundings by userId: {}", userId);
				throw new ForbiddenException(
						"no access to delete one more course contact person by userId: {}" + userId);
			}
			courseFundingDao.deleteByCourseIdAndUserIdIn(courseId, userIds);
		} else {
			log.error("one or more invalid user_ids against course_id");
			throw new NotFoundException("one or more invalid user_ids against course_id");
		}
	}

	private Course validateAndGetCourseById(String courseId) throws NotFoundException {
		log.info("inside get course by id");
		log.debug("going to call db for getting course for id: {}", courseId);
		Course course = courseDao.get(courseId);
		if (ObjectUtils.isEmpty(course)) {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		} else {
			return course;
		}
	}
}