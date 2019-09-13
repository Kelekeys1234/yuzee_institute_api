package com.seeka.app.service;

import java.util.List;

import com.seeka.app.dto.GlobalDataDto;

public interface IGlobalStudentData {

	void saveGlobalStudentData(GlobalDataDto globalStudentDataDto);

	void deleteAllGlobalStudentData();
	
	List<GlobalDataDto> getCountryWiseStudentList(String countryName);
	
	long checkForPresenceOfUserCountryInGlobalDataFile(String countryName);
}
