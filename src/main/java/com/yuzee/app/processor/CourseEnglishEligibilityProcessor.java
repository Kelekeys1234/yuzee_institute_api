package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import com.yuzee.app.dto.CourseEnglishEligibilityRequestWrapper;
import com.yuzee.common.lib.dto.institute.CourseEnglishEligibilityDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.RuntimeNotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.Utils;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseEnglishEligibilityProcessor {

	@Autowired
	CourseDao courseDao;


	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	@Lazy
	private CourseProcessor courseProcessor;

	@Autowired
	private CommonProcessor commonProcessor;

	@Autowired
	private MessageTranslator messageTranslator;

	public List<CourseEnglishEligibilityDto> getAllEnglishEligibilityByCourse(String courseId) {
		log.debug("Inside getAllEnglishEligibilityByCourse() method");
		List<CourseEnglishEligibilityDto> courseEnglishEligibilityResponse = new ArrayList<>();
		Course course = courseDao.get(courseId);
		log.info("Fetching englishEligibilties from DB for courseId = " + courseId);

		List<CourseEnglishEligibility> courseEnglishEligibilitiesFromDB = course.getCourseEnglishEligibilities();

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
			List<CourseEnglishEligibility> courseEnglishEligibilityBeforeUpdate = course.getCourseEnglishEligibilities()
					.stream().map(eligibility -> {
						CourseEnglishEligibility clone = new CourseEnglishEligibility();
						BeanUtils.copyProperties(eligibility, clone);
						return clone;
					}).collect(Collectors.toList());

			List<CourseEnglishEligibility> courseEnglishEligibilities = course.getCourseEnglishEligibilities();

			log.info("loop the requested list to collect the entitities to be saved/updated");
			courseEnglishEligibilityDtos.stream().forEach(e -> {
				CourseEnglishEligibility courseEnglishEligibility = new CourseEnglishEligibility();
				BeanUtils.copyProperties(e, courseEnglishEligibility);
				courseEnglishEligibilities.add(courseEnglishEligibility);

			});
			courseEnglishEligibilities.removeIf(e -> !contains(courseEnglishEligibilityDtos, e));

			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {
				List<CourseEnglishEligibilityDto> dtosToReplicate = courseEnglishEligibilities.stream()
						.map(e -> modelMapper.map(e, CourseEnglishEligibilityDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated.addAll(
						replicateCourseEnglishEligibilities(userId, request.getLinkedCourseIds(), dtosToReplicate));
			}
			courseDao.saveAll(coursesToBeSavedOrUpdated);

			if (!courseEnglishEligibilityBeforeUpdate.equals(courseEnglishEligibilities)) {
				commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
			}

			// commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error(messageTranslator.toLocale("english_eligibility.course.id.invalid", courseId, Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("english_eligibility.course.id.invalid", courseId));
		}
	}

	@Transactional
	public void deleteByCourseEnglishEligibilityIds(String userId, String courseId, List<String> englishEligibilityIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		if (!ObjectUtils.isEmpty(course)) {

			List<CourseEnglishEligibility> courseEnglishEligibilities = course.getCourseEnglishEligibilities();

			if (!courseEnglishEligibilities.isEmpty()) {
				courseEnglishEligibilities.clear();
			}
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			courseDao.saveAll(coursesToBeSavedOrUpdated);

			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
//			// commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error(messageTranslator.toLocale("english_eligibility.ids.invalid", Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("english_eligibility.ids.invalid"));
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
						if (existingCousrseEnglishEligibilityOp.isPresent()) {
							courseEnglishEligibility = existingCousrseEnglishEligibilityOp.get();
							
						}
						BeanUtils.copyProperties(dto, courseEnglishEligibility);
						if (!existingCousrseEnglishEligibilityOp.isPresent()) {
							courseEnglishEligibilities.add(courseEnglishEligibility);
						}
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
