package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseFunding;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseFundingDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.CourseFundingRequestWrapper;
import com.yuzee.common.lib.dto.institute.CourseFundingDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseFundingProcessor {
	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CourseFundingDao courseFundingDao;

	@Autowired
	private InstituteDao instituteDao;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private CommonProcessor commonProcessor;

	@Transactional
	public void addFundingToAllInstituteCourses(String userId, String instituteId, String fundingNameId)
			throws ValidationException, NotFoundException, InvokeException {
		log.info("inside CourseFundingProcessor.addFundingToAllInstituteCourses");
		Institute institute = instituteDao.get(instituteId);
		if (institute != null) {

			commonProcessor.validateAndGetFundingsByFundingNameIds(Arrays.asList(fundingNameId));

			List<Course> instituteCourses = courseDao.findByInstituteId(instituteId);
			List<CourseFunding> courseFundings = new ArrayList<>();
			instituteCourses.stream().forEach(c -> {
				CourseFunding courseFunding = new CourseFunding();
				courseFunding.setAuditFields(userId);
				courseFunding.setCourse(c);
				courseFunding.setFundingNameId(fundingNameId);
				courseFundings.add(courseFunding);
			});
			List<CourseFunding> saved = courseFundingDao.saveAll(courseFundings);
			
			log.info("Send notifications to all course");
			List<Course> courseToBeNotified = new ArrayList<>();
			Map<String, List<CourseFunding>> courseWiseFundings = saved.stream().collect(Collectors.groupingBy(funding ->  funding.getCourse().getId()));
			courseWiseFundings.keySet().stream().forEach(courseId -> {
					courseToBeNotified.add(courseDao.get(courseId));
			});
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", courseToBeNotified);
		} else {
			log.error("invalid institute id: {}", instituteId);
			throw new NotFoundException("invalid institute id: " + instituteId);
		}
	}

	@Transactional
	public void saveCourseFundings(String userId, String courseId, CourseFundingRequestWrapper request)
			throws NotFoundException, ValidationException, InvokeException {
		log.info("inside CourseFundingProcessor.saveCourseFundings");
		List<CourseFundingDto> courseFundingDtos = request.getCourseFundingDtos();
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {
			log.info("going to see if funding id is valid");
			commonProcessor.validateAndGetFundingsByFundingNameIds(
					courseFundingDtos.stream().map(CourseFundingDto::getFundingNameId).collect(Collectors.toList()));
			List<CourseFunding> courseFundings = course.getCourseFundings();
			courseFundingDtos.stream().forEach(e -> {
				CourseFunding courseFunding = new CourseFunding();
				courseFunding.setAuditFields(userId);
				courseFunding.setCourse(course);
				courseFunding.setFundingNameId(e.getFundingNameId());
				if (StringUtils.isEmpty(e.getId())) {
					courseFundings.add(courseFunding);
				}
			});

			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {
				List<CourseFundingDto> dtosToReplicate = courseFundings.stream()
						.map(e -> modelMapper.map(e, CourseFundingDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseFundings(userId, request.getLinkedCourseIds(), dtosToReplicate));
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
	public void deleteCourseFundingsByFundingNameIds(String userId, String courseId, List<String> fundingNameIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CourseFundingProcessor.deleteCourseFundingsByFundingNameIds");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<CourseFunding> courseFundings = course.getCourseFundings();
		if (courseFundings.stream().map(CourseFunding::getFundingNameId).collect(Collectors.toSet())
				.containsAll(fundingNameIds)) {
			if (courseFundings.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more fundings by userId: {}", userId);
				throw new ForbiddenException("no access to delete one more fundings by userId: {}" + userId);
			}

			courseFundings.removeIf(e -> Utils.contains(fundingNameIds, e.getFundingNameId()));
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(linkedCourseIds)) {
				List<CourseFundingDto> dtosToReplicate = courseFundings.stream()
						.map(e -> modelMapper.map(e, CourseFundingDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated.addAll(replicateCourseFundings(userId, linkedCourseIds, dtosToReplicate));
			}
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);

			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("one or more invalid course_funding_name_ids");
			throw new NotFoundException("one or more invalid course_funding_name_ids");
		}
	}

	private List<Course> replicateCourseFundings(String userId, List<String> courseIds,
			List<CourseFundingDto> courseFundingDtos) throws ValidationException, NotFoundException {
		log.info("inside courseProcessor.replicateCourseFundings");
		Set<String> fundingNameIds = courseFundingDtos.stream().map(CourseFundingDto::getFundingNameId)
				.collect(Collectors.toSet());
		if (!CollectionUtils.isEmpty(courseIds)) {
			List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
			courses.stream().forEach(course -> {

				List<CourseFunding> courseFundings = course.getCourseFundings();
				if (CollectionUtils.isEmpty(courseFundingDtos)) {
					courseFundings.clear();
				} else {
					courseFundings.removeIf(
							e -> !Utils.containsIgnoreCase(fundingNameIds.stream().collect(Collectors.toList()),
									e.getFundingNameId()));
					courseFundingDtos.stream().forEach(dto -> {
						Optional<CourseFunding> existingCourseFundingOp = courseFundings.stream()
								.filter(e -> e.getFundingNameId().equals(dto.getFundingNameId())).findAny();
						CourseFunding courseFunding = null;
						if (existingCourseFundingOp.isPresent()) {
							courseFunding = existingCourseFundingOp.get();
						} else {
							courseFunding = new CourseFunding();
							courseFunding.setCourse(course);
							courseFundings.add(courseFunding);
						}
						courseFunding.setAuditFields(userId);
						courseFunding.setFundingNameId(dto.getFundingNameId());
					});
				}
			});
			return courses;
		}
		return new ArrayList<>();
	}
}