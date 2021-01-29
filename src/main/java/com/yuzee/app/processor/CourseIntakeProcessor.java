package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseIntake;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseIntakeDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.CourseIntakeDto;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseIntakeProcessor {

	@Autowired
	CourseIntakeDao courseIntakeDao;

	@Autowired
	InstituteDao instituteDao;

	@Autowired
	CourseDao courseDao;

	public void saveCourseIntakes(String userId, String courseId, @Valid List<CourseIntakeDto> courseIntakeDtos)
			throws NotFoundException, ValidationException {
		log.info("inside CourseIntakeProcessor.saveCourseIntakes");
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {

			List<CourseIntake> courseIntakes = new ArrayList<>();
			courseIntakeDtos.stream().forEach(e -> {
				CourseIntake courseIntake = new CourseIntake();
				courseIntake.setAuditFields(userId);
				courseIntake.setCourse(course);
				courseIntakes.add(courseIntake);
			});
			log.info("going to save record in db");
			courseIntakeDao.saveAll(courseIntakes);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	public void deleteByCourseIntakeIds(String userId, String courseId, List<String> intakeIds)
			throws NotFoundException, ForbiddenException {
		log.info("inside CourseIntakeProcessor.deleteByCourseIntakeIds");
		List<CourseIntake> courseIntakes = courseIntakeDao.findByCourseIdAndIdIn(courseId, intakeIds);
		if (intakeIds.size() == courseIntakes.size()) {
			if (courseIntakes.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more intakes by intake ids: ", Arrays.asList(intakeIds));
				throw new ForbiddenException(
						"no access to delete one more intakes by intake ids: {}" + Arrays.asList(intakeIds));
			}
			courseIntakeDao.deleteByCourseIdAndIdIn(courseId, intakeIds);
		} else {
			log.error("one or more invalid course_intake_ids");
			throw new NotFoundException("one or more invalid course_intake_ids");
		}
	}
}