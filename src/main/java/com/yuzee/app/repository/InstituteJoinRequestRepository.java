package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteJoinRequest;
import com.yuzee.app.constant.InstituteJoinStatus;

@Repository
public interface InstituteJoinRequestRepository extends MongoRepository<InstituteJoinRequest, String> {
	
	public InstituteJoinRequest findByInstituteNameAndUserId (String instituteName, String userId);
	
	public List<InstituteJoinRequest> findByInstituteJoinStatus (InstituteJoinStatus instituteJoinStatus);

}
