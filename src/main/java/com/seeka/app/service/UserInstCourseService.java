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
	IUserInstCourseReviewDAO dao;

	@Override
	public void save(UserCourseReview reviewObj) {
		dao.save(reviewObj);
	}
	
	@Override
	public void update(UserCourseReview reviewObj) {
		dao.update(reviewObj);
	}
	
	@Override
	public UserCourseReview get(BigInteger userId) {
		return dao.get(userId);
	}
	
	@Override
	public List<UserCourseReview> getAllReviewsByFilter(UserReviewRequestDto filterObj){
		return dao.getAllReviewsByFilter(filterObj);
	}
	
	@Override
	public UserCourseReview getOverAllReview(UserReviewRequestDto filterObj) {
		return dao.getOverAllReview(filterObj);
	}
	
	@Override
	public Boolean findReviewByFilters(UserReviewRequestDto filterObj) {
		return dao.findReviewByFilters(filterObj);
	}
	
	@Override
	public List<UserCourseReview> getTopReviewsByFilter(BigInteger courseId, BigInteger instituteId){
		return dao.getTopReviewsByFilter(courseId,instituteId);
	}
	
}
