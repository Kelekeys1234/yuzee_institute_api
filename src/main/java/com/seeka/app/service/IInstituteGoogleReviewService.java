package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.seeka.app.dto.InstituteGoogleReviewDto;

public interface IInstituteGoogleReviewService {

	List<InstituteGoogleReviewDto> getInstituteGoogleReview(BigInteger instituteId, Integer startIndex, Integer pageSize);

	int getCountInstituteGoogleReview(BigInteger instituteId);

	Double getInstituteAvgGoogleReview(BigInteger instituteId);

	Map<BigInteger, Double> getInstituteAvgGoogleReviewForList(List<BigInteger> instituteIdList);

}
