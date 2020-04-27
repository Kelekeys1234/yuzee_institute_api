package com.seeka.app.dao;

import java.util.List;
import java.util.Optional;

import com.seeka.app.bean.InstitutePageRequest;
import com.seeka.app.constant.PageRequestStatus;

public interface InstitutePageRequestDao {

	public InstitutePageRequest addInstitutePageRequest (InstitutePageRequest institutePageRequest) ;
	
	public InstitutePageRequest getInstitutePageRequestByInstituteIdAndUserId(String instituteId, String userId);
	
	public List<InstitutePageRequest> getInstitutePageRequestByInstituteId(String instituteId);
	
	public List<InstitutePageRequest> getInstitutePageRequestByInstituteIdAndStatus(String instituteId, PageRequestStatus pageRequestStatus);
	
	public Optional<InstitutePageRequest> getInstitutePageRequestById(String institutePageRequestId);
}
