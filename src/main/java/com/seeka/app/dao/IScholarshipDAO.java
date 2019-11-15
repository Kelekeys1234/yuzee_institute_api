package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.seeka.app.bean.Scholarship;
import com.seeka.app.bean.ScholarshipIntakes;
import com.seeka.app.bean.ScholarshipLanguage;

public interface IScholarshipDAO {

	void saveScholarship(Scholarship scholarship);

	void saveScholarshipIntake(ScholarshipIntakes scholarshipIntakes);

	void saveScholarshipLanguage(ScholarshipLanguage scholarshipLanguage);

	void deleteScholarshipIntakes(BigInteger scholarShipId);

	void deleteScholarshipLanguage(BigInteger scholarShipId);

	Scholarship getScholarshipById(BigInteger id);

	List<ScholarshipIntakes> getIntakeByScholarship(BigInteger id);

	List<ScholarshipLanguage> getLanguageByScholarship(BigInteger id);

	void updateScholarship(Scholarship scholarship);

	List<Scholarship> getScholarshipList(Integer startIndex, Integer pageSize, BigInteger countryId, BigInteger instituteId, String validity, Boolean isActive,
			Date filterDate, String searchKeyword, String sortByField, String sortByType);

	int countScholarshipList(BigInteger countryId, BigInteger instituteId, String validity, Boolean isActive, Date filterDate, String searchKeyword);

	List<BigInteger> getRandomScholarShipsForCountry(List<BigInteger> countryIds, Integer limit);

	List<Scholarship> getAllScholarshipDetailsFromId(List<BigInteger> recommendedScholarships);

	List<BigInteger> getRandomScholarships(int i);
}
