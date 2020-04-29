package com.seeka.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteJoinRequest;
import com.seeka.app.constant.InstituteJoinStatus;

@Repository
public interface InstituteJoinRequestRepository extends JpaRepository<InstituteJoinRequest, String> {
	
	public InstituteJoinRequest findByInstituteNameAndUserId (String instituteName, String userId);
	
	public List<InstituteJoinRequest> findByInstituteJoinStatus (InstituteJoinStatus instituteJoinStatus);

}
