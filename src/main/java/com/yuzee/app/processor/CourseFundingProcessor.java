package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseFunding;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseFundingDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.FundingResponseDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.EligibilityHandler;

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
	private EligibilityHandler eligibilityHandler;

	public void addFundingToAllInstituteCourses(String userId, String instituteId, String fundingNameId)
			throws ValidationException {
		Institute institute = instituteDao.get(instituteId);
		if (institute != null) {
			try {
				Map<String, FundingResponseDto> fundingMap = eligibilityHandler
						.getFundingByFundingNameId(Arrays.asList(fundingNameId));
				if (fundingMap.size() != 1) {
					throw new ValidationException("funding_name_id is invalid");
				}
			} catch (InvokeException e1) {
				log.error("error invoking eligibility service so could'nt check if it funding_name_ids really exists");
			}
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
			throw new ValidationException("invalid institute id: " + instituteId);
		}
	}
}
