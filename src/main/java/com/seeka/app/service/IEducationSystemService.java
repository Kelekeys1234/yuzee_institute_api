package com.seeka.app.service;

import java.math.BigInteger;
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

	EducationSystem get(BigInteger id);

	List<EducationSystem> getAll();

	List<EducationSystem> getAllGlobeEducationSystems();

	List<EducationSystem> getEducationSystemsByCountryId(BigInteger countryId);

	ResponseEntity<?> saveEducationSystems(@Valid EducationSystemDto educationSystem);

//	ResponseEntity<?> saveEducationDetails(@Valid EducationSystemRequest educationSystemDetails);

	EducationSystemResponse getEducationSystemsDetailByUserId(BigInteger userId);

	ResponseEntity<?> deleteEducationSystemDetailByUserId(@Valid BigInteger userId);

	ResponseEntity<?> calculate(@Valid GradeDto gradeDto);

	ResponseEntity<?> getGrades(BigInteger countryId, BigInteger systemId);

	void saveUserEducationDetails(EducationSystemRequest educationSystemDetails);
}
