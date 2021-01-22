package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Careers;
import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseCareerOutcome;
import com.yuzee.app.dao.CareerDao;
import com.yuzee.app.dao.CourseCareerOutComeDao;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.CourseCareerOutcomeDto;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.RuntimeNotFoundException;
import com.yuzee.app.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseCareerOutcomeProcessor {

	@Autowired
	CourseCareerOutComeDao courseCareerOutcomeDao;

	@Autowired
	InstituteDao instituteDao;

	@Autowired
	CourseDao courseDao;

	@Autowired
	private CareerDao careerDao;

	public void saveUpdateCourseCareerOutcomes(String userId, String courseId,
			@Valid List<CourseCareerOutcomeDto> courseCareerOutcomeDtos) throws NotFoundException, ValidationException {
		log.info("inside CourseCareerOutcomeProcessor.saveUpdateCourseCareerOutcomes");
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {
			Set<String> careerIds = courseCareerOutcomeDtos.stream().map(CourseCareerOutcomeDto::getCareerId)
					.collect(Collectors.toSet());
			Map<String, Careers> careersMap = getCareerByIds(careerIds.stream().collect(Collectors.toList()));

			log.info("getting the ids of entitities to be updated");
			Set<String> updateRequestIds = courseCareerOutcomeDtos.stream().filter(e -> !StringUtils.isEmpty(e.getId()))
					.map(CourseCareerOutcomeDto::getId).collect(Collectors.toSet());

			log.info("verfiy if ids exists against course");
			Map<String, CourseCareerOutcome> existingCourseCareerOutcomesMap = courseCareerOutcomeDao
					.findByCourseIdAndIdIn(courseId, updateRequestIds.stream().collect(Collectors.toList())).stream()
					.collect(Collectors.toMap(CourseCareerOutcome::getId, e -> e));

			List<CourseCareerOutcome> courseCareerOutcomes = new ArrayList<>();
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
				}
				BeanUtils.copyProperties(e, courseCareerOutcome);
				courseCareerOutcome.setCareer(careersMap.get(e.getCareerId()));
				courseCareerOutcome.setCourse(course);
				courseCareerOutcome.setAuditFields(userId);
				courseCareerOutcomes.add(courseCareerOutcome);
			});
			courseCareerOutcomeDao.saveAll(courseCareerOutcomes);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	public void deleteByCourseCareerOutcomeIds(String userId, String courseId, List<String> careerOutcomeIds)
			throws NotFoundException, ForbiddenException {
		log.info("inside CourseCareerOutcomeProcessor.deleteByCourseCareerOutcomeIds");
		List<CourseCareerOutcome> courseCareerOutcomes = courseCareerOutcomeDao.findByCourseIdAndIdIn(courseId,
				careerOutcomeIds);
		if (careerOutcomeIds.size() == courseCareerOutcomes.size()) {
			if (courseCareerOutcomes.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more career outcomes");
				throw new ForbiddenException("no access to delete one more career outcomes");
			}
			courseCareerOutcomeDao.deleteByCourseIdAndIdIn(courseId, careerOutcomeIds);
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
}