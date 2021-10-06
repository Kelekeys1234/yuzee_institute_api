package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseEnglishEligibility;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseEnglishEligibilityDao;
import com.yuzee.app.dto.CourseEnglishEligibilityRequestWrapper;
import com.yuzee.common.lib.dto.institute.CourseEnglishEligibilityDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.RuntimeNotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseEnglishEligibilityProcessor {

	@Autowired
	CourseDao courseDao;

	@Autowired
	private CourseEnglishEligibilityDao courseEnglishEligibilityDAO;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	@Lazy
	private CourseProcessor courseProcessor;

	@Autowired
	private CommonProcessor commonProcessor;

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

	@Transactional
	public void saveUpdateCourseEnglishEligibilities(String userId, String courseId,
			@Valid CourseEnglishEligibilityRequestWrapper request) throws NotFoundException, ValidationException {
		log.info("inside CourseEnglishEligibilityDao.saveUpdateCourseEnglishEligibilities");
		List<CourseEnglishEligibilityDto> courseEnglishEligibilityDtos = request.getCourseEnglishEligibilityDtos();
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {
			List<CourseEnglishEligibility> courseEnglishEligibilityBeforeUpdate = course.getCourseEnglishEligibilities().stream().map(eligibility -> {
				CourseEnglishEligibility clone = new CourseEnglishEligibility();
				BeanUtils.copyProperties(eligibility, clone);
				return clone;
			}).collect(Collectors.toList());
			

			log.info("preparing map of exsiting course english eligibilities");
			Map<String, CourseEnglishEligibility> existingCourseEnglishEligibilitysMap = course
					.getCourseEnglishEligibilities().stream()
					.collect(Collectors.toMap(CourseEnglishEligibility::getId, e -> e));

			List<CourseEnglishEligibility> courseEnglishEligibilities = course.getCourseEnglishEligibilities();

			log.info("loop the requested list to collect the entitities to be saved/updated");
			courseEnglishEligibilityDtos.stream().forEach(e -> {
				CourseEnglishEligibility courseEnglishEligibility = new CourseEnglishEligibility();
				if (!StringUtils.isEmpty(e.getId())) {
					log.info(
							"entityId is present so going to see if it is present in db if yes then we have to update it");
					courseEnglishEligibility = existingCourseEnglishEligibilitysMap.get(e.getId());
					if (courseEnglishEligibility == null) {
						log.error("invalid course english eligbility id : {}", e.getId());
						throw new RuntimeNotFoundException("invalid course english eligbility id : " + e.getId());
					}
				}
				BeanUtils.copyProperties(e, courseEnglishEligibility);
				courseEnglishEligibility.setCourse(course);
				courseEnglishEligibility.setAuditFields(userId);
				if (StringUtils.isEmpty(e.getId())) {
					courseEnglishEligibilities.add(courseEnglishEligibility);
				}
			});

			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {
				List<CourseEnglishEligibilityDto> dtosToReplicate = courseEnglishEligibilities.stream()
						.map(e -> modelMapper.map(e, CourseEnglishEligibilityDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated.addAll(
						replicateCourseEnglishEligibilities(userId, request.getLinkedCourseIds(), dtosToReplicate));
			}
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			
			if(!courseEnglishEligibilityBeforeUpdate.equals(courseEnglishEligibilities)) {
				commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
			}

			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	@Transactional
	public void deleteByCourseEnglishEligibilityIds(String userId, String courseId, List<String> englishEligibilityIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CourseEnglishEligibilityDao.deleteByCourseEnglishEligibilityIds");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<CourseEnglishEligibility> courseEnglishEligibilities = course.getCourseEnglishEligibilities();
		if (courseEnglishEligibilities.stream().map(CourseEnglishEligibility::getId).collect(Collectors.toSet())
				.containsAll(englishEligibilityIds)) {
			if (courseEnglishEligibilities.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more english eligbilities");
				throw new ForbiddenException("no access to delete one more english eligbilities");
			}
			courseEnglishEligibilities.removeIf(e -> Utils.contains(englishEligibilityIds, e.getId()));
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(linkedCourseIds)) {
				List<CourseEnglishEligibilityDto> dtosToReplicate = courseEnglishEligibilities.stream()
						.map(e -> modelMapper.map(e, CourseEnglishEligibilityDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseEnglishEligibilities(userId, linkedCourseIds, dtosToReplicate));
			}
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
			
			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("one or more invalid course_english eligbility_ids");
			throw new NotFoundException("one or more invalid course_english eligbility_ids");
		}
	}

	private List<Course> replicateCourseEnglishEligibilities(String userId, List<String> courseIds,
			List<CourseEnglishEligibilityDto> courseEnglishEligibilityDtos)
			throws ValidationException, NotFoundException {
		log.info("inside courseProcessor.replicateCourseEnglishEligibilities");
		if (!CollectionUtils.isEmpty(courseIds)) {
			List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
			courses.stream().forEach(course -> {
				List<CourseEnglishEligibility> courseEnglishEligibilities = course.getCourseEnglishEligibilities();
				if (CollectionUtils.isEmpty(courseEnglishEligibilityDtos)) {
					courseEnglishEligibilities.clear();
				} else {
					courseEnglishEligibilities.removeIf(e -> !contains(courseEnglishEligibilityDtos, e));
					courseEnglishEligibilityDtos.stream().forEach(dto -> {
						Optional<CourseEnglishEligibility> existingCousrseEnglishEligibilityOp = courseEnglishEligibilities
								.stream().filter(t -> dto.getEnglishType().equalsIgnoreCase(t.getEnglishType()))
								.findAny();
						CourseEnglishEligibility courseEnglishEligibility = new CourseEnglishEligibility();
						String existingId = null;
						if (existingCousrseEnglishEligibilityOp.isPresent()) {
							courseEnglishEligibility = existingCousrseEnglishEligibilityOp.get();
							existingId = courseEnglishEligibility.getId();
						}
						BeanUtils.copyProperties(dto, courseEnglishEligibility);
						courseEnglishEligibility.setId(existingId);
						courseEnglishEligibility.setCourse(course);
						if (StringUtils.isEmpty(courseEnglishEligibility.getId())) {
							courseEnglishEligibilities.add(courseEnglishEligibility);
						}
						courseEnglishEligibility.setAuditFields(userId);
					});
				}
			});
			return courses;
		}
		return new ArrayList<>();
	}

	public static boolean contains(List<CourseEnglishEligibilityDto> lst, CourseEnglishEligibility target) {
		return lst.stream().anyMatch(e -> e.getEnglishType().equalsIgnoreCase(target.getEnglishType()));
	}
}
