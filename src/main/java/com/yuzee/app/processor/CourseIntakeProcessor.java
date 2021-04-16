package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseIntake;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dto.CourseIntakeRequestWrapper;
import com.yuzee.common.lib.dto.institute.CourseIntakeDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseIntakeProcessor {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CourseProcessor courseProcessor;
	
	private ModelMapper modelMapper;
	
	@Autowired
	private CommonProcessor commonProcessor;
	
	@Transactional
	public void saveCourseIntakes(String userId, String courseId, CourseIntakeRequestWrapper request)
			throws NotFoundException, ValidationException {
		log.info("inside CourseIntakeProcessor.saveCourseIntakes");
		List<CourseIntakeDto> courseIntakeDtos = request.getCourseIntakeDtos();
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {

			List<CourseIntake> courseIntakes = course.getCourseIntakes();
			courseIntakeDtos.stream().forEach(e -> {
				CourseIntake courseIntake = new CourseIntake();
				courseIntake.setAuditFields(userId);
				courseIntake.setCourse(course);
				if (StringUtils.isEmpty(e.getId())) {
					courseIntakes.add(courseIntake);
				}
			});
			
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {
				List<CourseIntakeDto> dtosToReplicate = courseIntakes.stream()
						.map(e -> modelMapper.map(e, CourseIntakeDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseIntakes(userId, request.getLinkedCourseIds(), dtosToReplicate));
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
	public void deleteByCourseIntakeIds(String userId, String courseId, List<String> intakeIds, List<String> linkedCourseIds)
			throws NotFoundException, ForbiddenException, ValidationException {
		log.info("inside CourseIntakeProcessor.deleteByCourseIntakeIds");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<CourseIntake> courseIntakes = course.getCourseIntakes();
		if (courseIntakes.stream().map(CourseIntake::getId).collect(Collectors.toSet())
				.containsAll(intakeIds)) {
			if (courseIntakes.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more intakes by intake ids: ", Arrays.asList(intakeIds));
				throw new ForbiddenException(
						"no access to delete one more intakes by intake ids: {}" + Arrays.asList(intakeIds));
			}

			courseIntakes.removeIf(e -> Utils.contains(intakeIds, e.getId()));
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(linkedCourseIds)) {
				List<CourseIntakeDto> dtosToReplicate = courseIntakes.stream()
						.map(e -> modelMapper.map(e, CourseIntakeDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated.addAll(replicateCourseIntakes(userId, linkedCourseIds, dtosToReplicate));
			}
			
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
			
			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("one or more invalid course_intake_ids");
			throw new NotFoundException("one or more invalid course_intake_ids");
		}
	}
	
	private List<Course> replicateCourseIntakes(String userId, List<String> courseIds,
			List<CourseIntakeDto> courseIntakeDtos) throws ValidationException, NotFoundException {
		log.info("inside courseProcessor.replicateCourseIntakes");
		List<Date> intakeDates = courseIntakeDtos.stream().map(CourseIntakeDto::getIntakeDate)
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(courseIds)) {
			List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
			courses.stream().forEach(course -> {
				List<CourseIntake> courseIntakes = course.getCourseIntakes();
				if (CollectionUtils.isEmpty(courseIntakeDtos)) {
					courseIntakes.clear();
				} else {
					courseIntakes.removeIf(e -> !Utils.contains(intakeDates, e.getIntakeDate()));
					courseIntakeDtos.stream().forEach(dto -> {
						Optional<CourseIntake> existingIntakeOp = courseIntakes.stream().filter(
								e -> e.getIntakeDate().toInstant().compareTo(dto.getIntakeDate().toInstant()) == 0)
								.findAny();
						CourseIntake courseIntake = null;
						if (existingIntakeOp.isPresent()) {
							courseIntake = existingIntakeOp.get();
						} else {
							courseIntake = new CourseIntake();
							courseIntake.setCourse(course);
							courseIntake.setIntakeDate(dto.getIntakeDate());
							courseIntakes.add(courseIntake);
						}
						courseIntake.setAuditFields(userId);
					});
				}
			});
			return courses;
		}
		return new ArrayList<>();
	}
}