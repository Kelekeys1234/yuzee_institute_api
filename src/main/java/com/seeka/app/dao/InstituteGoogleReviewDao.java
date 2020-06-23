package com.seeka.app.dao;

import java.util.List;
import java.util.Map;

import com.seeka.app.bean.InstituteGoogleReview;

public interface InstituteGoogleReviewDao {

	public void save(InstituteGoogleReview instituteGoogleReview);

	public void update(InstituteGoogleReview instituteGoogleReview);

	public int getCountOfGooglereview(String instituteId);

	public List<InstituteGoogleReview> getInstituteGoogleReview(String instituteId, int startIndex, Integer pageSize);

	public Double getInstituteAvgGoogleReview(String instituteId);

	public Map<String, Double> getInstituteAvgGoogleReviewForList(List<String> instituteIdList);

}
