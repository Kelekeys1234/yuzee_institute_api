package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstitutePageRequest;
import com.yuzee.app.constant.PageRequestStatus;

@Repository
public interface InstitutePageRequestRepository extends JpaRepository<InstitutePageRequest, String> {

	public InstitutePageRequest findByInstituteIdAndUserId(String instituteId, String userId);
	
	public List<InstitutePageRequest>  findByInstituteId(String instituteId);
	
	public List<InstitutePageRequest>  findByInstituteIdAndPageRequestStatus(String instituteId, PageRequestStatus pageRequestStatus );
}
