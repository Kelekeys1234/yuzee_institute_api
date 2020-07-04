package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.bean.Subject;
import com.seeka.app.dto.EducationSystemDto;

public interface EducationSystemDao {

	public void save(final EducationSystem hobbiesObj);

	public void update(final EducationSystem hobbiesObj);

	public EducationSystem get(final String id);

	public List<EducationSystem> getAll();

	public List<EducationSystem> getAllGlobeEducationSystems();

	public List<EducationSystem> getEducationSystemsByCountryId(final String countryId);

	public List<Subject> getSubject();

	public List<Subject> getSubjectByEducationSystem(final String educationSystemId);

	public List<EducationSystemDto> getEducationSystemByCountryNameAndStateName(String countryName, String stateName);


}
