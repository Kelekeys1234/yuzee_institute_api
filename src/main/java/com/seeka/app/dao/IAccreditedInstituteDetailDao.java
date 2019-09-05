package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.AccreditedInstituteDetail;

public interface IAccreditedInstituteDetailDao {

	void addAccreditedInstituteDetail(AccreditedInstituteDetail accreditedInstituteDetail);

	List<AccreditedInstituteDetail> getAccreditedInstituteDetailList(BigInteger entityId, String entityType, Integer startIndex, Integer pageSize);

	List<AccreditedInstituteDetail> getAccreditedInstituteDetail(BigInteger accreditedInstituteId);

	AccreditedInstituteDetail getAccreditedInstituteDetailbasedOnParams(BigInteger accreditedInstituteId, BigInteger entityId, String entityType);
	
	void deleteAccreditedInstitueDetailByEntityId(BigInteger entityId);

}
