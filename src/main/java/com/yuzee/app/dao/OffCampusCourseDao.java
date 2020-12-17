package com.yuzee.app.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.OffCampusCourse;

public interface OffCampusCourseDao {
	public void saveOrUpdate(OffCampusCourse offCampusCourse);

	public Optional<OffCampusCourse> getById(String offCampusCourseId);

	public void deleteById(String offCampusCourseId);

	public Page<OffCampusCourse> getOffCampusCoursesByInstituteId(String instituteId, Pageable pageable);
}
