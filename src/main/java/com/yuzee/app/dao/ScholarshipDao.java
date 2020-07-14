package com.yuzee.app.dao;

import java.util.Date;
import java.util.List;

import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.bean.ScholarshipIntakes;
import com.yuzee.app.bean.ScholarshipLanguage;
import com.yuzee.app.dto.ScholarshipResponseDTO;

public interface ScholarshipDao {

	public Scholarship saveScholarship(Scholarship scholarship);

	public void saveScholarshipIntake(ScholarshipIntakes scholarshipIntakes);

	public void saveScholarshipLanguage(ScholarshipLanguage scholarshipLanguage);

	public void deleteScholarshipIntakes(String scholarShipId);

	public void deleteScholarshipLanguage(String scholarShipId);

	public Scholarship getScholarshipById(String id);

	public List<ScholarshipIntakes> getIntakeByScholarship(String id);

	public List<ScholarshipLanguage> getLanguageByScholarship(String id);

	public void updateScholarship(Scholarship scholarship);

	public List<ScholarshipResponseDTO> getScholarshipList(Integer startIndex, Integer pageSize, String countryId, String instituteId, String validity,
			Boolean isActive, Date filterDate, String searchKeyword, String sortByField, String sortByType);

	public int countScholarshipList(String countryId, String instituteId, String validity, Boolean isActive, Date filterDate, String searchKeyword);

	public List<String> getRandomScholarShipsForCountry(List<String> countryIds, Integer limit);

	public List<Scholarship> getAllScholarshipDetailsFromId(List<String> recommendedScholarships);

	public List<String> getRandomScholarships(int i);
	
	public Long getScholarshipCountByLevelId(String levelId);
}
