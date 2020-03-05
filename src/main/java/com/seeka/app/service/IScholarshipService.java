package com.seeka.app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.seeka.app.bean.Level;
import com.seeka.app.bean.Scholarship;
import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.dto.ScholarshipResponseDTO;
import com.seeka.app.exception.ValidationException;

public interface IScholarshipService {

	Scholarship saveScholarship(ScholarshipDto scholarshipDto) throws ValidationException;

	ScholarshipResponseDTO getScholarshipById(String id) throws ValidationException;

	void updateScholarship(ScholarshipDto scholarshipDto, String scholarshipId) throws ValidationException;

	List<ScholarshipResponseDTO> getScholarshipList(Integer startIndex, Integer pageSize, String countryId, String instituteId, String validity,
			Boolean isActive, Date filterDate, String searchKeyword, String sortByField, String sortByType);

	int countScholarshipList(String countryId, String instituteId, String validity, Boolean isActive, Date filterDate, String searchKeyword);

	void deleteScholarship(String scholarshipId) throws ValidationException;

	List<String> getScholarshipIdsByCountryId(List<String> countryId, Integer limit);

	List<ScholarshipDto> getAllScholarshipDetailsFromId(List<String> recommendedScholarships);

	List<String> getRandomScholarShipIds(int i);
	
	public Map<String, Object> getScholarshipCountByLevelId(List<Level> levelList);
}
