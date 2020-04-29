package com.seeka.app.dao;

import java.util.List;
import java.util.Optional;

import com.seeka.app.bean.InstituteJoinRequest;
import com.seeka.app.constant.InstituteJoinStatus;

public interface InstituteJoinRequestDao {
	
	public InstituteJoinRequest getInstituteJoinRequestByInstituteNameAndUserId (String instituteName, String userId);
	
	public InstituteJoinRequest addInstituteJoinRequest (InstituteJoinRequest instituteJoinRequest);

	public List<InstituteJoinRequest> getInstituteJoinRequestByStatus (InstituteJoinStatus instituteJoinStatus);
	
	public Optional<InstituteJoinRequest> getInstituteJoinRequestById (String instituteJoinRequestId);
}
