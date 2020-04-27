package com.seeka.app.dap.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.InstitutePageRequest;
import com.seeka.app.constant.PageRequestStatus;
import com.seeka.app.dao.InstitutePageRequestDao;
import com.seeka.app.repository.InstitutePageRequestRepository;

@Component
public class InstitutePageRequestDaoImpl implements InstitutePageRequestDao {

	@Autowired
	private InstitutePageRequestRepository institutePageRequestRepository;
	
	@Override
	public InstitutePageRequest addInstitutePageRequest(InstitutePageRequest institutePageRequest) {
		return institutePageRequestRepository.save(institutePageRequest);
	}

	@Override
	public InstitutePageRequest getInstitutePageRequestByInstituteIdAndUserId(String instituteId, String userId) {
		return institutePageRequestRepository.findByInstituteIdAndUserId(instituteId, userId);
	}

	@Override
	public List<InstitutePageRequest> getInstitutePageRequestByInstituteId(String instituteId) {
		return institutePageRequestRepository.findByInstituteId(instituteId);
	}

	@Override
	public List<InstitutePageRequest> getInstitutePageRequestByInstituteIdAndStatus(String instituteId,
			PageRequestStatus pageRequestStatus) {
		return institutePageRequestRepository.findByInstituteIdAndPageRequestStatus(instituteId, pageRequestStatus);
	}

	@Override
	public Optional<InstitutePageRequest> getInstitutePageRequestById(String institutePageRequestId) {
		return institutePageRequestRepository.findById(institutePageRequestId);
	}

}

