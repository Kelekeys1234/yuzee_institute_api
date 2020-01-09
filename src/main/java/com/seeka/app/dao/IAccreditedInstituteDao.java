package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.AccreditedInstitute;

public interface IAccreditedInstituteDao {

	void save(AccreditedInstitute accreditedInstitute);

	void update(AccreditedInstitute accreditedInstitute);

	List<AccreditedInstitute> getAccreditedInstituteList(Integer startIndex, Integer pageSize);

	AccreditedInstitute getAccreditedInstituteDetail(BigInteger instituteId);

	AccreditedInstitute getAccreditedInstituteDetailBasedOnName(String name, BigInteger instituteId);
	
	List<AccreditedInstitute> getAllAccreditedInstitutes();

}
