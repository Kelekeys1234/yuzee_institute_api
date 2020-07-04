package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.bean.Subject;
import com.seeka.app.dto.EducationSystemDto;

public interface EducationSystemDao {
	
	public void save(EducationSystem hobbiesObj);

	public void update(EducationSystem hobbiesObj);

	public EducationSystem get(String id);

	public List<EducationSystem> getAll();

	public List<EducationSystem> getAllGlobeEducationSystems();

	public List<EducationSystem> getEducationSystemsByCountryId(String countryId);

	public List<Subject> getSubjectByEducationSystem(String educationSystemId);

	public List<Subject> getSubject();
	
	public List<EducationSystemDto> getEducationSystemByCountryNameAndStateName(String countryName, String stateName);

}
