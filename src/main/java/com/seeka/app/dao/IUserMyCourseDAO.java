package com.seeka.app.dao;import java.math.BigInteger;


import java.util.List;


import com.seeka.app.bean.UserMyCourse;

public interface IUserMyCourseDAO {
	public void save(UserMyCourse reviewObj);
	public void update(UserMyCourse reviewObj);
	public UserMyCourse get(BigInteger userId);
	public UserMyCourse getDataByUserIDAndCourseID(BigInteger userId,BigInteger courseId);
	public List<UserMyCourse> getDataByUserID(BigInteger userId);
	public List<BigInteger> getAllCourseIdsByUser(BigInteger userId);
}
