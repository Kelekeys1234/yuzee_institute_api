package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Careers;
import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseCareerOutcome;
import com.yuzee.app.dao.CareerDao;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dto.CourseCareerOutcomeRequestWrapper;
import com.yuzee.common.lib.dto.institute.CourseCareerOutcomeDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.RuntimeNotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.Utils;

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
	
	@Transactional
	public void saveUpdateCourseCareerOutcomes(String userId, String courseId,
			CourseCareerOutcomeRequestWrapper request) throws NotFoundException, ValidationException {
		log.info("inside CourseCareerOutcomeProcessor.saveUpdateCourseCareerOutcomes");
		List<CourseCareerOutcomeDto> courseCareerOutcomeDtos = request.getCourseCareerOutcomeDtos();
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {
			List<CourseCareerOutcome> careerOutcomeBeforeUpdate = course.getCourseCareerOutcomes().stream().map(outcome -> {
				CourseCareerOutcome clone = new CourseCareerOutcome();
				BeanUtils.copyProperties(outcome, clone);
				return clone;
			}).collect(Collectors.toList());
			
			Set<String> careerIds = courseCareerOutcomeDtos.stream().map(CourseCareerOutcomeDto::getCareerId)
					.collect(Collectors.toSet());
			Map<String, Careers> careersMap = getCareerByIds(careerIds.stream().collect(Collectors.toList()));

			List<CourseCareerOutcome> courseCareerOutcomes = course.getCourseCareerOutcomes();

			log.info("preparing map of exsiting course career outcomes");
			Map<String, CourseCareerOutcome> existingCourseCareerOutcomesMap = courseCareerOutcomes.stream()
					.collect(Collectors.toMap(CourseCareerOutcome::getId, e -> e));

			log.info("loop the requested list to collect the entitities to be saved/updated");
			courseCareerOutcomeDtos.stream().forEach(e -> {
				CourseCareerOutcome courseCareerOutcome = new CourseCareerOutcome();
				if (!StringUtils.isEmpty(e.getId())) {
					log.info(
							"entityId is present so going to see if it is present in db if yes then we have to update it");
					courseCareerOutcome = existingCourseCareerOutcomesMap.get(e.getId());
					if (courseCareerOutcome == null) {
						log.error("invalid course career outcome id : {}", e.getId());
						throw new RuntimeNotFoundException("invalid course career outcome id : " + e.getId());
					}
				}else {
					courseCareerOutcomes.add(courseCareerOutcome);
				}
				BeanUtils.copyProperties(e, courseCareerOutcome);
				courseCareerOutcome.setCareer(careersMap.get(e.getCareerId()));
				courseCareerOutcome.setCourse(course);
				courseCareerOutcome.setAuditFields(userId);
			});

			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {
				List<CourseCareerOutcomeDto> dtosToReplicate = courseCareerOutcomes.stream()
						.map(e -> modelMapper.map(e, CourseCareerOutcomeDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseCareerOutcomes(userId, request.getLinkedCourseIds(), dtosToReplicate));
			}
			
			log.info("going to save record in db");
			courseDao.saveAll(coursesToBeSavedOrUpdated);

			if(!careerOutcomeBeforeUpdate.equals(courseCareerOutcomes)) {
				log.info("Notify course information changed");
				commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
			}
			
			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	@Transactional
	public void deleteByCourseCareerOutcomeIds(String userId, String courseId, List<String> careerOutcomeIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CourseCareerOutcomeProcessor.deleteByCourseCareerOutcomeIds");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<CourseCareerOutcome> courseCareerOutcomes = course.getCourseCareerOutcomes();
		if (courseCareerOutcomes.stream().map(CourseCareerOutcome::getId).collect(Collectors.toSet())
				.containsAll(careerOutcomeIds)) {
			if (courseCareerOutcomes.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more career outcomes");
				throw new ForbiddenException("no access to delete one more career outcomes");
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
			log.error("one or more invalid course_career outcome_ids");
			throw new NotFoundException("one or more invalid course_career outcome_ids");
		}
	}

	private Map<String, Careers> getCareerByIds(List<String> careerIds) throws ValidationException {
		log.info("inside CourseCareerOutcomeProcessor.getCareerByIds");
		Map<String, Careers> careersMap = careerDao.findByIdIn(new ArrayList<>(careerIds)).stream()
				.collect(Collectors.toMap(Careers::getId, e -> e));
		if (careersMap.size() != careerIds.size()) {
			log.error("one or more career ids are invalid");
			throw new ValidationException("one or more career ids are invalid");
		} else {
			return careersMap;
		}
	}

	private List<Course> replicateCourseCareerOutcomes(String userId, List<String> courseIds,
			List<CourseCareerOutcomeDto> courseCareerOutcomeDtos) throws ValidationException, NotFoundException {
		log.info("inside courseProcessor.replicateCourseCareerOutcomes");
		Set<String> careerIds = courseCareerOutcomeDtos.stream().map(CourseCareerOutcomeDto::getCareerId)
				.collect(Collectors.toSet());
		Map<String, Careers> careersMap = getCareerByIds(careerIds.stream().collect(Collectors.toList()));
		if (!CollectionUtils.isEmpty(courseIds)) {
			List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
			courses.stream().forEach(course -> {

				List<CourseCareerOutcome> courseCareerOutcomes = course.getCourseCareerOutcomes();
				if (CollectionUtils.isEmpty(courseCareerOutcomeDtos)) {
					courseCareerOutcomes.clear();
				} else {
					courseCareerOutcomes
							.removeIf(e -> !Utils.containsIgnoreCase(careerIds.stream().collect(Collectors.toList()),
									e.getCareer().getId()));
					courseCareerOutcomeDtos.stream().forEach(dto -> {
						Optional<CourseCareerOutcome> existingScholarshipOp = courseCareerOutcomes.stream()
								.filter(e -> e.getCareer().getId().equals(dto.getCareerId())).findAny();
						CourseCareerOutcome courseCareerOutcome = null;
						if (existingScholarshipOp.isPresent()) {
							courseCareerOutcome = existingScholarshipOp.get();
						} else {
							courseCareerOutcome = new CourseCareerOutcome();
							courseCareerOutcome.setCourse(course);
							courseCareerOutcomes.add(courseCareerOutcome);
						}
						courseCareerOutcome.setAuditFields(userId);
						courseCareerOutcome.setCareer(careersMap.get(dto.getCareerId()));
					});
				}
			});
			return courses;
		}
		return new ArrayList<>();
	}
}