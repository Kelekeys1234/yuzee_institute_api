package com.yuzee.app.processor;

import java.util.ArrayList;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dto.CourseContactPersonRequestWrapper;
import com.yuzee.common.lib.dto.institute.CourseContactPersonDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.Utils;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseContactPersonProcessor {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private CommonProcessor commonProcessor;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MessageTranslator messageTranslator;

	@Transactional
	public void saveCourseContactPersons(String userId, String courseId, CourseContactPersonRequestWrapper request)
			throws NotFoundException, ValidationException, InvokeException {
		log.info("inside CourseContactPersonProcessor.saveCourseContactPersons for courseId: {}", courseId);
		List<CourseContactPersonDto> courseContactPersonDtos = request.getCourseContactPersonDtos();
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		log.info("going to see if user ids are valid");

//		commonProcessor.validateAndGetUsersByUserIds(userId,
//			courseContactPersonDtos.stream().map(CourseContactPersonDto::getUserId).collect(Collectors.toList()));
		log.debug("going to process the request");
		List<String> courseContactPersons = course.getCourseContactPersons();

		log.debug("going to save the list in db");
		CourseContactPersonDto dto= new CourseContactPersonDto();
		courseContactPersons.addAll(courseContactPersonDtos.stream().map(e->e.getUserId()).collect(Collectors.toList()));
		course.setCourseContactPersons(courseContactPersons);
		List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
		coursesToBeSavedOrUpdated.add(course);
		if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {
			List<CourseContactPersonDto> dtosToReplicate=new ArrayList<>();
			courseContactPersons.stream().forEach(e->{
				dto.setUserId(e);
			});
			dtosToReplicate.add(dto);
			coursesToBeSavedOrUpdated
					.addAll(replicateCourseContactPersons(userId, request.getLinkedCourseIds(), dtosToReplicate));
		}
		courseDao.saveAll(coursesToBeSavedOrUpdated);

		if (!CollectionUtils.isEmpty(coursesToBeSavedOrUpdated)) {
			log.info("Notify course information changed");
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
		}

		//  commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
	}

	@Transactional
	public void deleteCourseContactPersonsByUserIds(String userId, String courseId, List<String> userIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CourseContactPersonProcessor.deleteCourseContactPersonsByUserIds");

		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<String> courseContactPersons = course.getCourseContactPersons();
		if (courseContactPersons.stream().collect(Collectors.toSet())
				.containsAll(userIds)) {
			userIds.stream().forEach(a->{
				courseContactPersons.removeIf(e -> a.equals(e));
				course.setCourseContactPersons(courseContactPersons);
				List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
				coursesToBeSavedOrUpdated.add(course);
			
			
			if (!CollectionUtils.isEmpty(linkedCourseIds)) {
				List<CourseContactPersonDto> dtosToReplicate = courseContactPersons.stream()
						.map(e -> modelMapper.map(e, CourseContactPersonDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseContactPersons(userId, linkedCourseIds, dtosToReplicate));
			}
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
			//commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
			});
		} else {
			log.error(messageTranslator.toLocale("course_contact_person.user.id.invalid", Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("course_contact_person.user.id.invalid"));
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
				List<String> courseContactPersons = course.getCourseContactPersons();
				if (CollectionUtils.isEmpty(courseContactPersonDtos)) {
					courseContactPersons.clear();
				} else {
					courseContactPersons.removeIf(e -> !Utils
							.containsIgnoreCase(userIds.stream().collect(Collectors.toList()), e));
					courseContactPersonDtos.stream().forEach(dto -> {
				    Optional<String> existingContactPersonOp = courseContactPersons.stream()
								.filter(e -> e.equals(userIds)).findAny();
						String courseContactPerson = null;
						if (existingContactPersonOp.isPresent()) {
							courseContactPerson = existingContactPersonOp.get();
						}
					});
				}
		
			});
		
			return courses;
		
		}
		
		return new ArrayList<>();

	}
}