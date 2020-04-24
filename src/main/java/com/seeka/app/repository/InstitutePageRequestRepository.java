package com.seeka.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstitutePageRequest;

@Repository
public interface InstitutePageRequestRepository extends JpaRepository<InstitutePageRequest, String> {

	public InstitutePageRequest findByInstituteIdAndUserId(String instituteId, String userId);
	
	public List<InstitutePageRequest>  findByInstituteId(String instituteId);
}
