package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.bean.Subject;
import com.yuzee.app.dto.EducationSystemDto;

public interface EducationSystemDao {

	public void save(final EducationSystem hobbiesObj);

	public void update(final EducationSystem hobbiesObj);

	public EducationSystem get(final String id);

	public List<EducationSystem> getAll();

	public List<EducationSystem> getAllGlobeEducationSystems();

	public List<EducationSystem> getEducationSystemsByCountryName(final String countryId);

	public List<Subject> getSubject();

	public List<Subject> getSubjectByEducationSystem(final String educationSystemId);

	public List<EducationSystemDto> getEducationSystemByCountryNameAndStateName(String countryName, String stateName);

}
