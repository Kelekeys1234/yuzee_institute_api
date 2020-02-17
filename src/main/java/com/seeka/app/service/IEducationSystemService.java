package com.seeka.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.dto.EducationSystemDto;
import com.seeka.app.dto.EducationSystemRequest;
import com.seeka.app.dto.EducationSystemResponse;
import com.seeka.app.dto.GradeDto;

public interface IEducationSystemService {
	void save(EducationSystem hobbiesObj);

	void update(EducationSystem hobbiesObj);

	EducationSystem get(String id);

	List<EducationSystem> getAll();

	List<EducationSystem> getAllGlobeEducationSystems();

	List<EducationSystem> getEducationSystemsByCountryId(String countryId);

	ResponseEntity<?> saveEducationSystems(@Valid EducationSystemDto educationSystem);

//	ResponseEntity<?> saveEducationDetails(@Valid EducationSystemRequest educationSystemDetails);

	EducationSystemResponse getEducationSystemsDetailByUserId(String userId);

	ResponseEntity<?> deleteEducationSystemDetailByUserId(@Valid String userId);

	ResponseEntity<?> calculate(@Valid GradeDto gradeDto);

	ResponseEntity<?> getGrades(String countryId, String systemId);

	void saveUserEducationDetails(EducationSystemRequest educationSystemDetails);
}
