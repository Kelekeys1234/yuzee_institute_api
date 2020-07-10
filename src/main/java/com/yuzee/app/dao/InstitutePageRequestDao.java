package com.yuzee.app.dao;

import java.util.List;
import java.util.Optional;

import com.yuzee.app.bean.InstitutePageRequest;
import com.yuzee.app.constant.PageRequestStatus;

public interface InstitutePageRequestDao {

	public InstitutePageRequest addInstitutePageRequest (InstitutePageRequest institutePageRequest) ;
	
	public InstitutePageRequest getInstitutePageRequestByInstituteIdAndUserId(String instituteId, String userId);
	
	public List<InstitutePageRequest> getInstitutePageRequestByInstituteId(String instituteId);
	
	public List<InstitutePageRequest> getInstitutePageRequestByInstituteIdAndStatus(String instituteId, PageRequestStatus pageRequestStatus);
	
	public Optional<InstitutePageRequest> getInstitutePageRequestById(String institutePageRequestId);
}
