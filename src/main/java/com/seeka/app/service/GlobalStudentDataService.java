package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.dao.IGlobalStudentDataDAO;
import com.seeka.app.dto.GlobalDataDto;

@Service
@Transactional(rollbackFor = Throwable.class)
public class GlobalStudentDataService implements IGlobalStudentData {

	@Autowired
	private IGlobalStudentDataDAO globalStudentDataDao;
	
	@Override
	public void saveGlobalStudentData(GlobalDataDto globalStudentDataDto) {
		// TODO Auto-generated method stub
		
		globalStudentDataDto.setUpdatedBy("API");
		globalStudentDataDto.setCreatedBy("API");
		globalStudentDataDto.setCreatedOn(new java.util.Date(System.currentTimeMillis()));
		globalStudentDataDto.setUpdatedOn(new java.util.Date(System.currentTimeMillis()));
		globalStudentDataDao.save(globalStudentDataDto);
	}
	
	@Override
	public void deleteAllGlobalStudentData() {
		// TODO Auto-generated method stub
		globalStudentDataDao.deleteAll();
	}

	@Override
	public List<GlobalDataDto> getCountryWiseStudentList(String countryName) {
		// TODO Auto-generated method stub
		return globalStudentDataDao.getCountryWiseStudentList(countryName);
	}

	@Override
	public long checkForPresenceOfUserCountryInGlobalDataFile(String countryName) {
		// TODO Auto-generated method stub
		return globalStudentDataDao.getNonZeroCountOfStudentsForCountry(countryName);
	}
	
}
