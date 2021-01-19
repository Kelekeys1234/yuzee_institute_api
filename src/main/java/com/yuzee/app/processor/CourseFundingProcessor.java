package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseFunding;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseFundingDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.CourseFundingDto;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseFundingProcessor {

	@Autowired
	CourseFundingDao courseFundingDao;

	@Autowired
	InstituteDao instituteDao;

	@Autowired
	CourseDao courseDao;

	@Autowired
	private CommonProcessor commonProcessor;

	public void addFundingToAllInstituteCourses(String userId, String instituteId, String fundingNameId)
			throws ValidationException, NotFoundException {
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
			courseFundingDao.saveAll(courseFundings);
		} else {
			log.error("invalid institute id: {}", instituteId);
			throw new NotFoundException("invalid institute id: " + instituteId);
		}
	}

	public void saveCourseFundings(String userId, String courseId, @Valid List<CourseFundingDto> courseFundingDtos)
			throws NotFoundException, ValidationException {
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {
			log.info("going to see if funding id is valid");
			commonProcessor.validateAndGetFundingsByFundingNameIds(
					courseFundingDtos.stream().map(CourseFundingDto::getFundingNameId).collect(Collectors.toList()));
			List<CourseFunding> courseFundings = new ArrayList<>();
			courseFundingDtos.stream().forEach(e -> {
				CourseFunding courseFunding = new CourseFunding();
				courseFunding.setAuditFields(userId);
				courseFunding.setCourse(course);
				courseFunding.setFundingNameId(e.getFundingNameId());
				courseFundings.add(courseFunding);
			});
			courseFundingDao.saveAll(courseFundings);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	public void deleteCourseFundingsByFundingNameIds(String userId, String courseId, List<String> fundingNameIds)
			throws NotFoundException, ForbiddenException {
		List<CourseFunding> courseFundings = courseFundingDao.findByCourseIdFundingNameIdIn(courseId, fundingNameIds);
		if (fundingNameIds.size() != courseFundings.size()) {
			if (courseFundings.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more fundings by userId: {}", userId);
				throw new ForbiddenException("no access to delete one more fundings by userId: {}" + userId);
			}
			courseFundingDao.deleteByFundingNameIdIn(fundingNameIds);
		} else {
			log.error("one or more invalid course_funding_name_ids");
			throw new NotFoundException("one or more invalid course_funding_name_ids");
		}
	}
}