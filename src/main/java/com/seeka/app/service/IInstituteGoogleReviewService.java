package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteGoogleReview;

public interface IInstituteGoogleReviewService {

	void addInstituteGoogleReview();

	InstituteGoogleReview getInstituteGoogleReviewDetail(BigInteger instituteId);

	List<InstituteGoogleReview> getInstituteGoogleReview(Integer pageNumber, Integer pageSize);

	int getCountOfGooglereview();

}
