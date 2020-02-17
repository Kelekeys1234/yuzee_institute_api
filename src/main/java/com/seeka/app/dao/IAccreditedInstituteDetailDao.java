package com.seeka.app.dao;

import java.util.List;

import javax.validation.Valid;

import com.seeka.app.bean.AccreditedInstituteDetail;

public interface IAccreditedInstituteDetailDao {

	void addAccreditedInstituteDetail(AccreditedInstituteDetail accreditedInstituteDetail);

	List<AccreditedInstituteDetail> getAccreditedInstituteDetailList(String entityId, String entityType, Integer startIndex, Integer pageSize);

	List<AccreditedInstituteDetail> getAccreditedInstituteDetail(String accreditedInstituteId);

	AccreditedInstituteDetail getAccreditedInstituteDetailbasedOnParams(String accreditedInstituteId, String entityId, String entityType);
	
	void deleteAccreditedInstitueDetailByEntityId(String entityId);

    List<String> getAccreditation(@Valid String id);

}
