package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.bean.Subject;
import com.seeka.app.dto.EducationSystemDto;

public interface IEducationSystemDAO {
	void save(EducationSystem hobbiesObj);

	void update(EducationSystem hobbiesObj);

	EducationSystem get(String id);

	List<EducationSystem> getAll();

	List<EducationSystem> getAllGlobeEducationSystems();

	List<EducationSystem> getEducationSystemsByCountryId(String countryId);

	List<Subject> getSubjectByEducationSystem(String educationSystemId);

	List<Subject> getSubject();
	
	List<EducationSystemDto> getEducationSystemByCountryNameAndStateName(String countryName, String stateName);

}
