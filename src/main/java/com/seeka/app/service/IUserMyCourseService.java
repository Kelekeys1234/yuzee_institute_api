package com.seeka.app.service;import java.math.BigInteger;


import java.util.List;


import com.seeka.app.bean.UserMyCourse;

public interface IUserMyCourseService {
	public void save(UserMyCourse user);
	public void update(UserMyCourse user);
	public UserMyCourse get(BigInteger userId);
	public UserMyCourse getDataByUserIDAndCourseID(BigInteger userId,BigInteger courseId);
	public List<UserMyCourse> getDataByUserID(BigInteger userId);
	public List<BigInteger> getAllCourseIdsByUser(BigInteger userId);
}

