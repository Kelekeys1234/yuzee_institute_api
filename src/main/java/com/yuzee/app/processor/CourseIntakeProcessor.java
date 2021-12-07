package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseIntake;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseIntakeDao;
import com.yuzee.common.lib.dto.institute.CourseIntakeDto;
import com.yuzee.common.lib.enumeration.IntakeType;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseIntakeProcessor {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private CourseIntakeDao courseIntakeDao;

	@Autowired
	private CommonProcessor commonProcessor;

	@Transactional
	public void saveCourseIntake(String userId, String courseId, CourseIntakeDto courseIntakeDto)
			throws NotFoundException, ValidationException {
		log.info("inside CourseIntakeProcessor.saveCourseIntake");
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {

			CourseIntake courseIntake = course.getCourseIntake();
			if (ObjectUtils.isEmpty(courseIntake)) {
				courseIntake = new CourseIntake();
			}
			courseIntake.setAuditFields(userId);
			courseIntake.setCourse(course);
			courseIntake.setType(IntakeType.valueOf(courseIntakeDto.getType()));
			courseIntake.setDates(courseIntakeDto.getDates());
			course.setCourseIntake(courseIntake);
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(courseIntakeDto.getLinkedCourseIds())) {
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseIntakes(userId, courseIntakeDto.getLinkedCourseIds(), courseIntakeDto));
			}
			courseDao.saveAll(coursesToBeSavedOrUpdated);

			log.info("Send notification for course content updates");
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);

			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	@Transactional
	public void deleteCourseIntake(String userId, String courseId, List<String> linkedCourseIds)
			throws NotFoundException, ForbiddenException, ValidationException {
		log.info("inside CourseIntakeProcessor.deleteByCourseIntakeIds");
		List<Course> courses = courseProcessor.validateAndGetCourseByIds(linkedCourseIds);
		if (CollectionUtils.isEmpty(linkedCourseIds)) {
			linkedCourseIds = new ArrayList<>();
		}
		linkedCourseIds.add(courseId);
		courseIntakeDao.deleteByCourseIdIn(linkedCourseIds);
		courses.stream().forEach(e -> e.setCourseIntake(null));

		courseDao.saveAll(courses);

		commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", courses);

		commonProcessor.saveElasticCourses(courses);
	}

	private List<Course> replicateCourseIntakes(String userId, List<String> courseIds, CourseIntakeDto courseIntakeDto)
			throws ValidationException, NotFoundException {
		log.info("inside courseProcessor.replicateCourseIntakes");
		if (!CollectionUtils.isEmpty(courseIds)) {
			List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
			courses.stream().forEach(course -> {
				CourseIntake courseIntake = course.getCourseIntake();
				if (ObjectUtils.isEmpty(courseIntake)) {
					courseIntake = new CourseIntake();
				}
				courseIntake.setAuditFields(userId);
				courseIntake.setCourse(course);
				courseIntake.setType(IntakeType.valueOf(courseIntakeDto.getType()));
				courseIntake.setDates(courseIntakeDto.getDates());
				course.setCourseIntake(courseIntake);
			});
			return courses;
		}
		return new ArrayList<>();
	}
}