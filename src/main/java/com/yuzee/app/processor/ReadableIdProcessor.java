package com.yuzee.app.processor;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dao.ScholarshipDao;
import com.yuzee.common.lib.dto.ReadableIdExistsDto;
import com.yuzee.common.lib.exception.ConstraintVoilationException;
import com.yuzee.common.lib.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReadableIdProcessor {
	
	@Autowired
	CourseDao courseDao;
	
	@Autowired
	private InstituteDao instiuteDao;
	
	@Autowired
	private ScholarshipDao scholarshipDAO;

	public void setReadableIdForCourse(Course course) {
		log.info("going to generate code for course");
		boolean reGenerateCode = false;
		do {
			reGenerateCode = false;
			String onlyName = Utils.convertToLowerCaseAndRemoveSpace(course.getName());
			String readableId = Utils.generateReadableId(onlyName);
			List<Course> sameCodeInsts = courseDao.findByReadableIdIn(Arrays.asList(onlyName, readableId));
			if (ObjectUtils.isEmpty(sameCodeInsts)) {
				course.setReadableId(onlyName);
			} else if (sameCodeInsts.size() == 1) {
				course.setReadableId(readableId);
			} else {
				reGenerateCode = true;
			}
		} while (reGenerateCode);
	}
	
	@Transactional(rollbackFor = {ConstraintVoilationException.class,Exception.class})
	public void setReadableIdForInsitute(Institute institute) {
		log.info("going to generate code for institute");
		boolean reGenerateCode = false;
		do {
			reGenerateCode = false;
			String onlyName = Utils.convertToLowerCaseAndRemoveSpace(institute.getName());
			String readableId = Utils.generateReadableId(onlyName);
			List<Institute> sameCodeInsts = instiuteDao.findByReadableIdIn(Arrays.asList(onlyName, readableId));
			if (ObjectUtils.isEmpty(sameCodeInsts)) {
				institute.setReadableId(onlyName);
			} else if (sameCodeInsts.size() == 1) {
				institute.setReadableId(readableId);
			} else {
				reGenerateCode = true;
			}
		} while (reGenerateCode);
	}

	public void setReadableIdForScholarship(Scholarship scholarship) {
		log.info("going to generate code for scholarship");
		boolean reGenerateCode = false;
		do {
			reGenerateCode = false;
			String onlyName = Utils.convertToLowerCaseAndRemoveSpace(scholarship.getName());
			String readableId = Utils.generateReadableId(onlyName);
			List<Scholarship> sameCodeEntities = scholarshipDAO.findByReadableIdIn(Arrays.asList(onlyName, readableId));
			if (ObjectUtils.isEmpty(sameCodeEntities)) {
				scholarship.setReadableId(onlyName);
			} else if (sameCodeEntities.size() == 1) {
				scholarship.setReadableId(readableId);
			} else {
				reGenerateCode = true;
			}
		} while (reGenerateCode);
	}

	public ReadableIdExistsDto checkIfInstituteReadableIdExists(String readableId) {
		List<Institute> sameCodeInsts = instiuteDao.findByReadableIdIn(Arrays.asList(readableId));
		ReadableIdExistsDto dto = new ReadableIdExistsDto();
		dto.setAlreadyExists(false);
		if (!CollectionUtils.isEmpty(sameCodeInsts)) {
			dto.setAlreadyExists(true);
		}
		return dto;
	}
}
