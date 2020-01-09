package com.seeka.app.dao;import java.math.BigInteger;


import java.util.List;


import com.seeka.app.bean.UserCourseReview;
import com.seeka.app.dto.UserReviewRequestDto;

public interface IUserInstCourseReviewDAO {
	public void save(UserCourseReview reviewObj);
	public void update(UserCourseReview reviewObj);
	public UserCourseReview get(BigInteger userId);
	public List<UserCourseReview> getAllReviewsByFilter(UserReviewRequestDto filterObj);
	public UserCourseReview getOverAllReview(UserReviewRequestDto filterObj);
	public Boolean findReviewByFilters(UserReviewRequestDto filterObj);
	public List<UserCourseReview> getTopReviewsByFilter(BigInteger courseId, BigInteger instituteId);
}
