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
	IUserMyCourseDAO dao;

	@Override
	public void save(UserMyCourse reviewObj) {
		dao.save(reviewObj);
	}
	
	@Override
	public void update(UserMyCourse reviewObj) {
		dao.update(reviewObj);
	}
	
	@Override
	public UserMyCourse get(BigInteger userId) {
		return dao.get(userId);
	}
	
	@Override
	public UserMyCourse getDataByUserIDAndCourseID(BigInteger userId,BigInteger courseId) {
		return dao.getDataByUserIDAndCourseID(userId,courseId);
	}
	
	@Override
	public List<UserMyCourse> getDataByUserID(BigInteger userId){
		return dao.getDataByUserID(userId);
	}
	
	@Override
	public List<BigInteger> getAllCourseIdsByUser(BigInteger userId){
		return dao.getAllCourseIdsByUser(userId);
	}
}
