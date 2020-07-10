package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.InstituteFacility;


public interface InstituteFacilityDao {
	
	public List<InstituteFacility> getAllInstituteFacility(String instituteId);
	
	public void saveInstituteFacility(List<InstituteFacility> listOfInstituteFacility);
	
	public void deleteFacilityByIdAndInstituteId (String instituteFacilityId, String instituteId);
}
