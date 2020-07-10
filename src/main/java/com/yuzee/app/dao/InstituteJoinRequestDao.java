package com.yuzee.app.dao;

import java.util.List;
import java.util.Optional;

import com.yuzee.app.bean.InstituteJoinRequest;
import com.yuzee.app.constant.InstituteJoinStatus;

public interface InstituteJoinRequestDao {
	
	public InstituteJoinRequest getInstituteJoinRequestByInstituteNameAndUserId (String instituteName, String userId);
	
	public InstituteJoinRequest addInstituteJoinRequest (InstituteJoinRequest instituteJoinRequest);

	public List<InstituteJoinRequest> getInstituteJoinRequestByStatus (InstituteJoinStatus instituteJoinStatus);
	
	public Optional<InstituteJoinRequest> getInstituteJoinRequestById (String instituteJoinRequestId);
}
