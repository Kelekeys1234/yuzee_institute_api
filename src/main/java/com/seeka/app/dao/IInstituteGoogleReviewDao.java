package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.seeka.app.bean.InstituteGoogleReview;

public interface IInstituteGoogleReviewDao {

	void save(InstituteGoogleReview instituteGoogleReview);

	void update(InstituteGoogleReview instituteGoogleReview);

	int getCountOfGooglereview(BigInteger instituteId);

	List<InstituteGoogleReview> getInstituteGoogleReview(BigInteger instituteId, int startIndex, Integer pageSize);

	Double getInstituteAvgGoogleReview(BigInteger instituteId);

	Map<BigInteger, Double> getInstituteAvgGoogleReviewForList(List<BigInteger> instituteIdList);

}
