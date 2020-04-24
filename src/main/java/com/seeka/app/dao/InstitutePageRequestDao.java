package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.InstitutePageRequest;

public interface InstitutePageRequestDao {

	public InstitutePageRequest addInstitutePageRequest (InstitutePageRequest institutePageRequest) ;
	
	public InstitutePageRequest getInstitutePageRequestByInstituteIdAndUserId(String instituteId, String userId);
	
	public List<InstitutePageRequest> getInstitutePageRequestByInstituteId(String instituteId);
	
}
