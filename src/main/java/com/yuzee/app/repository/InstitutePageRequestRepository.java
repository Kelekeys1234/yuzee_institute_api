package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstitutePageRequest;
import com.yuzee.app.constant.PageRequestStatus;

@Repository
public interface InstitutePageRequestRepository extends MongoRepository<InstitutePageRequest, String> {

	public InstitutePageRequest findByInstituteIdAndUserId(String instituteId, String userId);
	
	public List<InstitutePageRequest>  findByInstituteId(String instituteId);
	
	public List<InstitutePageRequest>  findByInstituteIdAndPageRequestStatus(String instituteId, PageRequestStatus pageRequestStatus );
}
