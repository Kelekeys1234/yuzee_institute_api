package com.yuzee.app.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.OffCampusCourse;
import com.yuzee.app.dao.OffCampusCourseDao;
import com.yuzee.app.repository.OffCampusCourseRepository;

@Service
public class OffCampusCourseDaoImpl implements OffCampusCourseDao {

	@Autowired
	private OffCampusCourseRepository offCampusCourseRepository;

	@Override
	public OffCampusCourse saveOrUpdate(OffCampusCourse offCampusCourse) {
		return offCampusCourseRepository.save(offCampusCourse);
	}

	@Override
	public Optional<OffCampusCourse> getById(String offCampusCourseId) {
		return offCampusCourseRepository.findById(offCampusCourseId);
	}

	@Override
	public void deleteById(String offCampusCourseId) {
		offCampusCourseRepository.deleteById(offCampusCourseId);
	}

	@Override
	public Page<OffCampusCourse> getOffCampusCoursesByInstituteId(String instituteId, Pageable pageable) {
		return offCampusCourseRepository.findByCourseInstituteId(instituteId, pageable);
	}

}
