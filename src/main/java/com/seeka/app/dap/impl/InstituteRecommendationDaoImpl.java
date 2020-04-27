package com.seeka.app.dap.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.InstituteRecommendRequest;
import com.seeka.app.constant.RecommendRequestStatus;
import com.seeka.app.dao.InstituteRecommendationRequestDao;
import com.seeka.app.repository.InstituteRecommendationRequestRepository;

@Component
public class InstituteRecommendationDaoImpl implements InstituteRecommendationRequestDao {

	@Autowired
	private InstituteRecommendationRequestRepository instituteRecommendationRequestRepository;

	@Override
	public List<InstituteRecommendRequest> getInstituteRecommendationByInstituteName(String instituteName) {
		return instituteRecommendationRequestRepository.findByInstituteName(instituteName);
	}

	@Override
	public void addInstituteRecommendationRequest(InstituteRecommendRequest instituteRecommendRequest) {
		instituteRecommendationRequestRepository.save(instituteRecommendRequest);
		
	}

	@Override
	public List<InstituteRecommendRequest> getInstituteRecommendationRequestByStatus(
			RecommendRequestStatus recommendRequestStatus) {	
		return instituteRecommendationRequestRepository.findByRecommendRequestStatus(recommendRequestStatus);
		
	}

	@Override
	public Optional<InstituteRecommendRequest> getInstituteRecommendationById(String instituteRecommendationId) {
		return instituteRecommendationRequestRepository.findById(instituteRecommendationId);
	}
	 
}
