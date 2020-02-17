package com.seeka.app.service;

import java.util.List;
import java.util.Map;

import com.seeka.app.dto.InstituteGoogleReviewDto;

public interface IInstituteGoogleReviewService {

	List<InstituteGoogleReviewDto> getInstituteGoogleReview(String instituteId, Integer startIndex, Integer pageSize);

	int getCountInstituteGoogleReview(String instituteId);

	Double getInstituteAvgGoogleReview(String instituteId);

	Map<String, Double> getInstituteAvgGoogleReviewForList(List<String> instituteIdList);

}
