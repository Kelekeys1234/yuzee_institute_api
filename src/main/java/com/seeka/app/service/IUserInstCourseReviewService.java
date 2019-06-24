package com.seeka.app.service;import java.math.BigInteger;


import java.util.List;


import com.seeka.app.bean.UserCourseReview;
import com.seeka.app.dto.UserReviewRequestDto;

public interface IUserInstCourseReviewService {
	public void save(UserCourseReview user);
	public void update(UserCourseReview user);
	public UserCourseReview get(BigInteger userId);
	public List<UserCourseReview> getAllReviewsByFilter(UserReviewRequestDto filterObj);
	public UserCourseReview getOverAllReview(UserReviewRequestDto filterObj);
	public Boolean findReviewByFilters(UserReviewRequestDto filterObj);
	public List<UserCourseReview> getTopReviewsByFilter(BigInteger courseId, BigInteger instituteId);
}

