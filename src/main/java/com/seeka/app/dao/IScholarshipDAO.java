package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.seeka.app.bean.Scholarship;
import com.seeka.app.bean.ScholarshipIntakes;
import com.seeka.app.bean.ScholarshipLanguage;
import com.seeka.app.dto.ScholarshipResponseDTO;

public interface IScholarshipDAO {

	Scholarship saveScholarship(Scholarship scholarship);

	void saveScholarshipIntake(ScholarshipIntakes scholarshipIntakes);

	void saveScholarshipLanguage(ScholarshipLanguage scholarshipLanguage);

	void deleteScholarshipIntakes(String scholarShipId);

	void deleteScholarshipLanguage(String scholarShipId);

	Scholarship getScholarshipById(String id);

	List<ScholarshipIntakes> getIntakeByScholarship(String id);

	List<ScholarshipLanguage> getLanguageByScholarship(String id);

	void updateScholarship(Scholarship scholarship);

	List<ScholarshipResponseDTO> getScholarshipList(Integer startIndex, Integer pageSize, String countryId, String instituteId, String validity,
			Boolean isActive, Date filterDate, String searchKeyword, String sortByField, String sortByType);

	int countScholarshipList(String countryId, String instituteId, String validity, Boolean isActive, Date filterDate, String searchKeyword);

	List<String> getRandomScholarShipsForCountry(List<String> countryIds, Integer limit);

	List<Scholarship> getAllScholarshipDetailsFromId(List<String> recommendedScholarships);

	List<String> getRandomScholarships(int i);
	
	BigInteger getScholarshipCountByLevelId(String levelId);
}
