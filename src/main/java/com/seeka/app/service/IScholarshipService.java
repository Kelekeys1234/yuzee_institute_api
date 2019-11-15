package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.dto.ScholarshipResponseDTO;
import com.seeka.app.exception.ValidationException;

public interface IScholarshipService {

	void saveScholarship(ScholarshipDto scholarshipDto) throws ValidationException;

	ScholarshipResponseDTO getScholarshipById(BigInteger id);

	void updateScholarship(ScholarshipDto scholarshipDto, BigInteger scholarshipId) throws ValidationException;

	List<ScholarshipResponseDTO> getScholarshipList(Integer startIndex, Integer pageSize, BigInteger countryId, BigInteger instituteId, String validity,
			Boolean isActive, Date filterDate, String searchKeyword, String sortByField, String sortByType);

	int countScholarshipList(BigInteger countryId, BigInteger instituteId, String validity, Boolean isActive, Date filterDate, String searchKeyword);

	void deleteScholarship(BigInteger scholarshipId) throws ValidationException;

	List<BigInteger> getScholarshipIdsByCountryId(List<BigInteger> countryId, Integer limit);

	List<ScholarshipDto> getAllScholarshipDetailsFromId(List<BigInteger> recommendedScholarships);

	List<BigInteger> getRandomScholarShipIds(int i);
}
