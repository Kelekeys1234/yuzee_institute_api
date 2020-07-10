package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.InstituteRecommendRequest;
import com.yuzee.app.constant.RecommendRequestStatus;
import com.yuzee.app.dao.InstituteRecommendationRequestDao;
import com.yuzee.app.repository.InstituteRecommendationRequestRepository;

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
