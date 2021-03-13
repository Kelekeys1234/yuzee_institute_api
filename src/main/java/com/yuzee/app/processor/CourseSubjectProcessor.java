package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseSubject;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.CourseSubjectDto;
import com.yuzee.app.dto.CourseSubjectRequestWrapper;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.RuntimeNotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.util.Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseSubjectProcessor {

	@Autowired
	InstituteDao instituteDao;

	@Autowired
	CourseDao courseDao;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CommonProcessor commonProcessor;

	@Transactional
	public void saveUpdateCourseSubjects(String userId, String courseId, CourseSubjectRequestWrapper request)
			throws NotFoundException, ValidationException {
		log.info("inside CourseSubjectProcessor.saveUpdateCourseSubjects");
		List<CourseSubjectDto> courseSubjectDtos = request.getCourseSubjectDtos();
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {

			log.info("preparing map of exsiting course subjects");
			Map<String, CourseSubject> existingCourseSubjectsMap = course.getCourseSubjects().stream()
					.collect(Collectors.toMap(CourseSubject::getId, e -> e));

			List<CourseSubject> courseSubjects = course.getCourseSubjects();
			log.info("loop the requested list to collect the entitities to be saved/updated");
			courseSubjectDtos.stream().forEach(e -> {
				CourseSubject courseSubject = new CourseSubject();
				if (!StringUtils.isEmpty(e.getId())) {
					log.info(
							"entityId is present so going to see if it is present in db if yes then we have to update it");
					courseSubject = existingCourseSubjectsMap.get(e.getId());
					if (courseSubject == null) {
						log.error("invalid course subject id : {}", e.getId());
						throw new RuntimeNotFoundException("invalid course subject id : " + e.getId());
					}
				}
				BeanUtils.copyProperties(e, courseSubject);
				courseSubject.setCourse(course);
				courseSubject.setAuditFields(userId);
				if (StringUtils.isEmpty(e.getId())) {
					courseSubjects.add(courseSubject);
				}
			});
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {
				List<CourseSubjectDto> dtosToReplicate = courseSubjectDtos.stream()
						.map(e -> modelMapper.map(e, CourseSubjectDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseSubjects(userId, request.getLinkedCourseIds(), dtosToReplicate));
			}
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	@Transactional
	public void deleteByCourseSubjectIds(String userId, String courseId, List<String> courseSubjectIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CourseSubjectProcessor.deleteByCourseSubjectIds");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<CourseSubject> courseSubjects = course.getCourseSubjects();
		if (courseSubjects.stream().map(CourseSubject::getId).collect(Collectors.toSet())
				.containsAll(courseSubjectIds)) {
			if (courseSubjects.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more subjects");
				throw new ForbiddenException("no access to delete one more subjects");
			}
			courseSubjects.removeIf(e -> Util.contains(courseSubjectIds, e.getId()));
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(linkedCourseIds)) {
				List<CourseSubjectDto> dtosToReplicate = courseSubjects.stream()
						.map(e -> modelMapper.map(e, CourseSubjectDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated.addAll(replicateCourseSubjects(userId, linkedCourseIds, dtosToReplicate));
			}
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("one or more invalid course_subject_ids");
			throw new NotFoundException("one or more invalid course_subject_ids");
		}
	}

	private List<Course> replicateCourseSubjects(String userId, List<String> courseIds,
			List<CourseSubjectDto> courseSubjectDtos) throws ValidationException, NotFoundException {
		log.info("inside courseProcessor.replicateCourseEnglishEligibilities");
		if (!CollectionUtils.isEmpty(courseIds)) {
			List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
			courses.stream().forEach(course -> {
				List<CourseSubject> courseSubjects = course.getCourseSubjects();
				if (CollectionUtils.isEmpty(courseSubjectDtos)) {
					courseSubjects.clear();
				} else {
					courseSubjects.removeIf(e -> !contains(courseSubjects, e));
					courseSubjectDtos.stream().forEach(dto -> {
						Optional<CourseSubject> existingCousrseSubjectOp = courseSubjects.stream()
								.filter(t -> t.getSemester().equalsIgnoreCase(dto.getSemester())
										&& t.getName().equalsIgnoreCase(dto.getName()))
								.findAny();
						CourseSubject courseSubject = new CourseSubject();
						String existingId = null;
						if (existingCousrseSubjectOp.isPresent()) {
							courseSubject = existingCousrseSubjectOp.get();
							existingId = courseSubject.getId();
						}
						BeanUtils.copyProperties(dto, courseSubject);
						courseSubject.setId(existingId);
						courseSubject.setCourse(course);
						if (StringUtils.isEmpty(courseSubject.getId())) {
							courseSubjects.add(courseSubject);
						}
						courseSubject.setAuditFields(userId);
					});
				}
			});
			return courses;
		}
		return new ArrayList<>();
	}

	public static boolean contains(List<CourseSubject> lst, CourseSubject target) {
		return lst.stream().anyMatch(e -> e.getSemester().equalsIgnoreCase(target.getSemester())
				&& e.getName().equalsIgnoreCase(target.getName()));
	}
}