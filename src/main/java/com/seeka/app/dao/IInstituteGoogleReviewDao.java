package com.seeka.app.dao;

import java.util.List;
import java.util.Map;

import com.seeka.app.bean.InstituteGoogleReview;

public interface IInstituteGoogleReviewDao {

	void save(InstituteGoogleReview instituteGoogleReview);

	void update(InstituteGoogleReview instituteGoogleReview);

	int getCountOfGooglereview(String instituteId);

	List<InstituteGoogleReview> getInstituteGoogleReview(String instituteId, int startIndex, Integer pageSize);

	Double getInstituteAvgGoogleReview(String instituteId);

	Map<String, Double> getInstituteAvgGoogleReviewForList(List<String> instituteIdList);

}
