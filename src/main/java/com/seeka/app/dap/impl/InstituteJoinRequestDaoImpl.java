package com.seeka.app.dap.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.InstituteJoinRequest;
import com.seeka.app.constant.InstituteJoinStatus;
import com.seeka.app.dao.InstituteJoinRequestDao;
import com.seeka.app.repository.InstituteJoinRequestRepository;

@Component
public class InstituteJoinRequestDaoImpl implements InstituteJoinRequestDao {
	
	@Autowired
	private InstituteJoinRequestRepository instituteJoinRequestRepository;

	@Override
	public InstituteJoinRequest getInstituteJoinRequestByInstituteNameAndUserId(String instituteName, String userId) {
		return instituteJoinRequestRepository.findByInstituteNameAndUserId(instituteName, userId);
	}

	@Override
	public InstituteJoinRequest addInstituteJoinRequest(InstituteJoinRequest instituteJoinRequest) {
		return instituteJoinRequestRepository.save(instituteJoinRequest);
	}

	@Override
	public List<InstituteJoinRequest> getInstituteJoinRequestByStatus(InstituteJoinStatus instituteJoinStatus) {
		return instituteJoinRequestRepository.findByInstituteJoinStatus(instituteJoinStatus);
	}

	@Override
	public Optional<InstituteJoinRequest> getInstituteJoinRequestById(String instituteJoinRequestId) {
		return instituteJoinRequestRepository.findById(instituteJoinRequestId);
	}

}
