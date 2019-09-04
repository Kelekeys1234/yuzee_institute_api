package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteGoogleReview;

public interface IInstituteGoogleReviewDao {

	void save(InstituteGoogleReview instituteGoogleReview);

	void update(InstituteGoogleReview instituteGoogleReview);

	InstituteGoogleReview getInstituteGoogleReviewDetail(BigInteger instituteId);

	List<InstituteGoogleReview> getInstituteGoogleReview(int startIndex, Integer pageSize);

	int getCountOfGooglereview();

}
