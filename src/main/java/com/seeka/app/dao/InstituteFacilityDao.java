package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.InstituteFacility;


public interface InstituteFacilityDao {
	
	public List<InstituteFacility> getAllInstituteFacility(String instituteId);
	
	public void saveInstituteFacility(List<InstituteFacility> listOfInstituteFacility);
	
	public void deleteFacilityByIdAndInstituteId (String instituteFacilityId, String instituteId);
}
