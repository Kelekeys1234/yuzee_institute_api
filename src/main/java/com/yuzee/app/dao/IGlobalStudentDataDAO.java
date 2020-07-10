package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.GlobalData;

public interface IGlobalStudentDataDAO {

	void save(final GlobalData globalDataDato);
	
	void deleteAll();
	
	List<GlobalData> getCountryWiseStudentList(String countryName);
	
	long getNonZeroCountOfStudentsForCountry(String countryName);
	
	List<String> getDistinctMigratedCountryForStudentCountry(String countryName);
	
	List<String> getDistinctMigratedCountryForStudentCountryOrderByNumberOfStudents(String countryName);
}
