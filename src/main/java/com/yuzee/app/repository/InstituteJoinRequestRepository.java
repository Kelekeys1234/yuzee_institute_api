package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteJoinRequest;
import com.yuzee.app.constant.InstituteJoinStatus;

@Repository
public interface InstituteJoinRequestRepository extends JpaRepository<InstituteJoinRequest, String> {
	
	public InstituteJoinRequest findByInstituteNameAndUserId (String instituteName, String userId);
	
	public List<InstituteJoinRequest> findByInstituteJoinStatus (InstituteJoinStatus instituteJoinStatus);

}
