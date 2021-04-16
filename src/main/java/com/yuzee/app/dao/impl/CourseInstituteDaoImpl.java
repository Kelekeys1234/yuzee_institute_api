package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseInstitute;
import com.yuzee.app.dao.CourseInstituteDao;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.app.repository.CourseInstituteRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class CourseInstituteDaoImpl implements CourseInstituteDao {

	@Autowired
	private CourseInstituteRepository courseInstituteRepository;

	@Override
	public List<CourseInstitute> saveAll(List<CourseInstitute> courseInstitutes) throws ValidationException {
		try {
			return courseInstituteRepository.saveAll(courseInstitutes);
		} catch (DataIntegrityViolationException e) {
			log.error("same institute is already linked with the course");
			throw new ValidationException("same institute is already linked with the course");
		}
	}

	@Override
	public List<CourseInstitute> findLinkedInstitutes(String courseId) {
		return courseInstituteRepository.findLinkedInstitutes(courseId);
	}

	@Override
	public CourseInstitute findByDestinationCourseId(String destinationCourseId) {
		return courseInstituteRepository.findByDestinationCourseId(destinationCourseId);
	}

	@Override
	public void deleteAll(List<CourseInstitute> courseInstitutes) {
		courseInstituteRepository.deleteAll(courseInstitutes);
	}
}
