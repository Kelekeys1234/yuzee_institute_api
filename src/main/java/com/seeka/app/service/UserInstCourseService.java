package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserCourseReview;
import com.seeka.app.dao.IUserInstCourseReviewDAO;
import com.seeka.app.dto.UserReviewRequestDto;

@Service
@Transactional
public class UserInstCourseService implements IUserInstCourseReviewService {

	@Autowired
	private IUserInstCourseReviewDAO iUserInstCourseReviewDAO;

	@Override
	public void save(UserCourseReview reviewObj) {
		iUserInstCourseReviewDAO.save(reviewObj);
	}
	
	@Override
	public void update(UserCourseReview reviewObj) {
		iUserInstCourseReviewDAO.update(reviewObj);
	}
	
	@Override
	public UserCourseReview get(BigInteger userId) {
		return iUserInstCourseReviewDAO.get(userId);
	}
	
	@Override
	public List<UserCourseReview> getAllReviewsByFilter(UserReviewRequestDto filterObj){
		return iUserInstCourseReviewDAO.getAllReviewsByFilter(filterObj);
	}
	
	@Override
	public UserCourseReview getOverAllReview(UserReviewRequestDto filterObj) {
		return iUserInstCourseReviewDAO.getOverAllReview(filterObj);
	}
	
	@Override
	public Boolean findReviewByFilters(UserReviewRequestDto filterObj) {
		return iUserInstCourseReviewDAO.findReviewByFilters(filterObj);
	}
	
	@Override
	public List<UserCourseReview> getTopReviewsByFilter(BigInteger courseId, BigInteger instituteId){
		return iUserInstCourseReviewDAO.getTopReviewsByFilter(courseId,instituteId);
	}
	
}
