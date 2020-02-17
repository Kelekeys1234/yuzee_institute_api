package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.AccreditedInstituteDetail;
import com.seeka.app.exception.ValidationException;

public interface IAccreditedInstituteDetailService {

	AccreditedInstituteDetail addAccreditedInstituteDetail(AccreditedInstituteDetail accreditedInstituteDetail) throws ValidationException;

	List<AccreditedInstituteDetail> getAccreditedInstituteDetailList(String entityId, String entityType, Integer pageNumber, Integer pageSize);

	List<AccreditedInstituteDetail> getAccreditedInstituteDetail(String accreditedInstituteId);

}
