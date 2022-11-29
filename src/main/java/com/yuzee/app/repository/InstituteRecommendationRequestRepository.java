package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteRecommendRequest;
import com.yuzee.app.constant.RecommendRequestStatus;

@Repository
public interface InstituteRecommendationRequestRepository extends MongoRepository<InstituteRecommendRequest, String> {

	public List<InstituteRecommendRequest> findByInstituteName (String instituteName);
	
	public List<InstituteRecommendRequest> findByRecommendRequestStatus (RecommendRequestStatus recommendRequestStatus);
}
