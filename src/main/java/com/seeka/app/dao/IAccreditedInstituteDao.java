package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.AccreditedInstitute;

public interface IAccreditedInstituteDao {

	void save(AccreditedInstitute accreditedInstitute);

	void update(AccreditedInstitute accreditedInstitute);

	List<AccreditedInstitute> getAccreditedInstituteList(Integer startIndex, Integer pageSize);

	AccreditedInstitute getAccreditedInstituteDetail(String
			instituteId);

	AccreditedInstitute getAccreditedInstituteDetailBasedOnName(String name, String instituteId);
	
	List<AccreditedInstitute> getAllAccreditedInstitutes();

}
