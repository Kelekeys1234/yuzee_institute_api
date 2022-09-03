package com.yuzee.app.dao;

import java.util.List;
import java.util.Optional;

import com.yuzee.app.bean.EducationSystem;
import com.yuzee.app.bean.Subject;
import com.yuzee.common.lib.dto.institute.EducationSystemDto;

public interface EducationSystemDao {

	public EducationSystem save(final EducationSystem hobbiesObj);
	
	public void saveAll(List<EducationSystem> educationSystems);

	public void update(final EducationSystem hobbiesObj);

	public Optional<EducationSystem> get(final String id);

	public List<EducationSystem> getAll();

	public List<EducationSystem> getAllGlobeEducationSystems();

	public List<EducationSystem> getEducationSystemsByCountryName(final String countryId);

	public List<Subject> getSubject();

	public List<Subject> getSubjectByEducationSystem(final String educationSystemId);

	public List<EducationSystemDto> getEducationSystemByCountryNameAndStateName(String countryName, String stateName);

	public EducationSystem findByNameAndCountryNameAndStateName(String name,String countryName,String stateName);
}
