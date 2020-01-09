package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.dto.GlobalDataDto;

public interface IGlobalStudentDataDAO {

	void save(final GlobalDataDto globalDataDato);
	
	void deleteAll();
	
	List<GlobalDataDto> getCountryWiseStudentList(String countryName);
	
	long getNonZeroCountOfStudentsForCountry(String countryName);
}
