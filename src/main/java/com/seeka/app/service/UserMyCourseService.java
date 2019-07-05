package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserMyCourse;
import com.seeka.app.dao.IUserMyCourseDAO;

@Service
@Transactional
public class UserMyCourseService implements IUserMyCourseService {

	@Autowired
	private IUserMyCourseDAO iUserMyCourseDAO;

	@Override
	public void save(UserMyCourse reviewObj) {
		iUserMyCourseDAO.save(reviewObj);
	}
	
	@Override
	public void update(UserMyCourse reviewObj) {
		iUserMyCourseDAO.update(reviewObj);
	}
	
	@Override
	public UserMyCourse get(BigInteger userId) {
		return iUserMyCourseDAO.get(userId);
	}
	
	@Override
	public UserMyCourse getDataByUserIDAndCourseID(BigInteger userId,BigInteger courseId) {
		return iUserMyCourseDAO.getDataByUserIDAndCourseID(userId,courseId);
	}
	
	@Override
	public List<UserMyCourse> getDataByUserID(BigInteger userId){
		return iUserMyCourseDAO.getDataByUserID(userId);
	}
	
	@Override
	public List<BigInteger> getAllCourseIdsByUser(BigInteger userId){
		return iUserMyCourseDAO.getAllCourseIdsByUser(userId);
	}
}
