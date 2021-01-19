package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseFunding;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseFundingDao;
import com.yuzee.app.dao.InstituteDao;
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

			commonProcessor.validateFundingNameIds(Arrays.asList(fundingNameId));

			List<Course> instituteCourses = courseDao.findByInstituteId(instituteId);
			List<CourseFunding> courseFundings = new ArrayList<>();
			instituteCourses.stream().forEach(c -> {
				CourseFunding courseFunding = new CourseFunding();
				courseFunding.setAuditFields(userId, null);
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
}