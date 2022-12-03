package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.google.common.base.Objects;
import com.yuzee.app.bean.Careers;
import com.yuzee.app.bean.Course;
import com.yuzee.app.dao.CareerDao;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dto.CourseCareerOutcomeRequestWrapper;
import com.yuzee.common.lib.dto.institute.CourseCareerOutcomeDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.RuntimeNotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.model.elastic.CourseCareerOutcome;
import com.yuzee.common.lib.util.Utils;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseCareerOutcomeProcessor {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private CommonProcessor commonProcessor;

	@Autowired
	private CareerDao careerDao;

	@Autowired
	private MessageTranslator messageTranslator;

	@Transactional
	public void saveUpdateCourseCareerOutcomes(String userId, String courseId,
			CourseCareerOutcomeRequestWrapper request) throws NotFoundException, ValidationException {
		log.info("inside CourseCareerOutcomeProcessor.saveUpdateCourseCareerOutcomes");
		List<CourseCareerOutcomeDto> courseCareerOutcomeDtos = request.getCourseCareerOutcomeDtos();
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {
			List<Careers> careerOutcomeBeforeUpdate = course.getCourseCareerOutcomes().stream().map(outcome -> {
				Careers clone = new Careers();
				BeanUtils.copyProperties(outcome, clone);
				return clone;
			}).collect(Collectors.toList());

			List<Careers> courseCareerOutcomes = course.getCourseCareerOutcomes();

			log.info("preparing map of exsiting course career outcomes");
			Map<String, Careers> existingCourseCareerOutcomesMap = courseCareerOutcomes.stream()
					.collect(Collectors.toMap(Careers::getId, e -> e));

			log.info("loop the requested list to collect the entitities to be saved/updated");
			courseCareerOutcomeDtos.stream().forEach(e -> {
				Careers courseCareerOutcome = new Careers();
				courseCareerOutcome.setId(UUID.randomUUID().toString());
				courseCareerOutcome.setCareer(e.getCareerId());
				courseCareerOutcomes.add(courseCareerOutcome);
				BeanUtils.copyProperties(e, courseCareerOutcome);
			});
			course.setCourseCareerOutcomes(courseCareerOutcomes);
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {

				coursesToBeSavedOrUpdated.addAll(
						replicateCourseCareerOutcomes(userId, request.getLinkedCourseIds(), courseCareerOutcomeDtos));
			}

			log.info("going to save record in db");
			courseDao.saveAll(coursesToBeSavedOrUpdated);

			if (!careerOutcomeBeforeUpdate.equals(courseCareerOutcomes)) {
				log.info("Notify course information changed");
				commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
			}

			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error(messageTranslator.toLocale("course_id.invalid", courseId, Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("course_id.invalid", courseId));
		}
	}

	@Transactional
	public void deleteByCourseCareerOutcomeIds(String userId, String courseId, List<String> careerOutcomeIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CourseCareerOutcomeProcessor.deleteByCourseCareerOutcomeIds");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<Careers> courseCareerOutcomes = course.getCourseCareerOutcomes();
		if (courseCareerOutcomes.stream().map(Careers::getId).collect(Collectors.toSet())
				.containsAll(careerOutcomeIds)) {
			if (courseCareerOutcomes.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error(messageTranslator.toLocale("career_outcomes.no.access", Locale.US));
				throw new ForbiddenException(messageTranslator.toLocale("career_outcomes.no.access"));
			}
			courseCareerOutcomes.removeIf(e -> Utils.contains(careerOutcomeIds, e.getId()));
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(linkedCourseIds)) {
				List<CourseCareerOutcomeDto> dtosToReplicate = courseCareerOutcomes.stream()
						.map(e -> modelMapper.map(e, CourseCareerOutcomeDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseCareerOutcomes(userId, linkedCourseIds, dtosToReplicate));
			}
			courseDao.saveAll(coursesToBeSavedOrUpdated);

			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);

			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error(messageTranslator.toLocale("course_career.outcome.id.invalid", Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("course_career.outcome.id.invalid"));
		}
	}

	private List<Careers> getCareerByIds(List<String> careerIds) {
		log.info("inside CourseCareerOutcomeProcessor.getCareerByIds");
		List<Careers> careersMaps = careerDao.findByIdIn(careerIds);
		List<String> careersId = careersMaps.stream().map(e -> e.getId()).toList();
		if (!careersId.equals(careerIds)) {
			log.error(messageTranslator.toLocale("course_career.career.id.invalid", Locale.US));
			throw new ValidationException(messageTranslator.toLocale("course_career.career.id.invalid"));
		}
		return careersMaps;

	}

	private List<Course> replicateCourseCareerOutcomes(String userId, List<String> courseIds,
			List<CourseCareerOutcomeDto> courseCareerOutcomeDtos) throws ValidationException, NotFoundException {
		log.info("inside courseProcessor.replicateCourseCarerOutcomes");
		Set<String> careerIds = courseCareerOutcomeDtos.stream().map(e -> e.getCareerId()).collect(Collectors.toSet());
		List<Careers> careersMap = getCareerByIds(careerIds.stream().collect(Collectors.toList()));
		if (!CollectionUtils.isEmpty(courseIds)) {
			List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
			courses.stream().forEach(course -> {
				List<Careers> courseCareerOutcomes = course.getCourseCareerOutcomes();
				if (CollectionUtils.isEmpty(courseCareerOutcomeDtos)) {
					courseCareerOutcomes.clear();
				} else {
					courseCareerOutcomes.removeIf(
							e -> !Utils.containsIgnoreCase(careerIds.stream().collect(Collectors.toList()), e.getId()));
					courseCareerOutcomeDtos.stream().forEach(dto -> {
						Optional<Careers> existingScholarshipOp = courseCareerOutcomes.stream()
								.filter(e -> e.getId().equals(dto.getCareer().getId())).findAny();
						Careers courseCareerOutcome = null;
						if (existingScholarshipOp.isPresent()) {
							courseCareerOutcome = existingScholarshipOp.get();
						}
						for (Careers career : careersMap) {
							courseCareerOutcome = new Careers();
							courseCareerOutcome.setId(UUID.randomUUID().toString());
							courseCareerOutcome.setCareer(career.getCareer());
							courseCareerOutcomes.add(courseCareerOutcome);
						}

					});
				}
			});
			return courses;
		}
		return new ArrayList<>();
	}
}