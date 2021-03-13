package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseContactPerson;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dto.CourseContactPersonDto;
import com.yuzee.app.dto.CourseContactPersonRequestWrapper;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.util.Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseContactPersonProcessor {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CommonProcessor commonProcessor;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public void saveCourseContactPersons(String userId, String courseId, CourseContactPersonRequestWrapper request)
			throws NotFoundException, ValidationException, InvokeException {
		log.info("inside CourseContactPersonProcessor.saveCourseContactPersons for courseId: {}", courseId);
		List<CourseContactPersonDto> courseContactPersonDtos = request.getCourseContactPersonDtos();
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		log.info("going to see if user ids are valid");
		commonProcessor.validateAndGetUsersByUserIds(
				courseContactPersonDtos.stream().map(CourseContactPersonDto::getUserId).collect(Collectors.toList()));
		log.debug("going to process the request");
		List<CourseContactPerson> courseContactPersons = course.getCourseContactPersons();
		courseContactPersonDtos.stream().forEach(e -> {
			CourseContactPerson courseContactPerson = new CourseContactPerson();
			courseContactPerson.setAuditFields(userId);
			courseContactPerson.setCourse(course);
			courseContactPerson.setUserId(e.getUserId());
			courseContactPersons.add(courseContactPerson);
		});
		log.debug("going to save the list in db");
		List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
		coursesToBeSavedOrUpdated.add(course);
		if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {
			List<CourseContactPersonDto> dtosToReplicate = courseContactPersons.stream()
					.map(e -> modelMapper.map(e, CourseContactPersonDto.class)).collect(Collectors.toList());
			coursesToBeSavedOrUpdated
					.addAll(replicateCourseContactPersons(userId, request.getLinkedCourseIds(), dtosToReplicate));
		}
		courseDao.saveAll(coursesToBeSavedOrUpdated);
		commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
	}

	@Transactional
	public void deleteCourseContactPersonsByUserIds(String userId, String courseId, List<String> userIds,
			List<String> linkedCourseIds) throws NotFoundException, ForbiddenException, ValidationException {
		log.info("inside CourseContactPersonProcessor.deleteCourseContactPersonsByUserIds");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<CourseContactPerson> courseContactPersons = course.getCourseContactPersons();
		if (courseContactPersons.stream().map(CourseContactPerson::getUserId).collect(Collectors.toSet())
				.containsAll(userIds)) {
			if (courseContactPersons.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more contact persons by userId: {}", userId);
				throw new ForbiddenException(
						"no access to delete one more course contact person by userId: {}" + userId);
			}
			courseContactPersons.removeIf(e -> Util.contains(userIds, e.getId()));
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(linkedCourseIds)) {
				List<CourseContactPersonDto> dtosToReplicate = courseContactPersons.stream()
						.map(e -> modelMapper.map(e, CourseContactPersonDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseContactPersons(userId, linkedCourseIds, dtosToReplicate));
			}
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("one or more invalid user_ids not found in course contact persons");
			throw new NotFoundException("one or more invalid user_ids not found in course contact persons");
		}
	}

	private List<Course> replicateCourseContactPersons(String userId, List<String> courseIds,
			List<CourseContactPersonDto> courseContactPersonDtos) throws ValidationException, NotFoundException {
		log.info("inside courseProcessor.replicateCourseContactPersons");
		Set<String> userIds = courseContactPersonDtos.stream().map(CourseContactPersonDto::getUserId)
				.collect(Collectors.toSet());
		if (!CollectionUtils.isEmpty(courseIds)) {
			List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
			courses.stream().forEach(course -> {
				List<CourseContactPerson> courseContactPersons = course.getCourseContactPersons();
				if (CollectionUtils.isEmpty(courseContactPersonDtos)) {
					courseContactPersons.clear();
				} else {
					courseContactPersons.removeIf(e -> !Util
							.containsIgnoreCase(userIds.stream().collect(Collectors.toList()), e.getUserId()));
					courseContactPersonDtos.stream().forEach(dto -> {
						Optional<CourseContactPerson> existingContactPersonOp = courseContactPersons.stream()
								.filter(e -> e.getUserId().equals(dto.getUserId())).findAny();
						CourseContactPerson courseContactPerson = null;
						if (existingContactPersonOp.isPresent()) {
							courseContactPerson = existingContactPersonOp.get();
						} else {
							courseContactPerson = new CourseContactPerson();
							courseContactPerson.setCourse(course);
							courseContactPersons.add(courseContactPerson);
						}
						courseContactPerson.setAuditFields(userId);
						courseContactPerson.setUserId(dto.getUserId());
					});
				}
			});
			return courses;
		}
		return new ArrayList<>();
	}
}