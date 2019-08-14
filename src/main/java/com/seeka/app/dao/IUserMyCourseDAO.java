package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserMyCourse;

/**
 *
 * @author SeekADegree
 *
 */
public interface IUserMyCourseDAO {
	void save(UserMyCourse reviewObj);

	void update(UserMyCourse reviewObj);

	UserMyCourse get(BigInteger userId);

	UserMyCourse getDataByUserIDAndCourseID(BigInteger userId, BigInteger courseId);

	List<UserMyCourse> getDataByUserID(BigInteger userId);

	List<BigInteger> getAllCourseIdsByUser(BigInteger userId);
}
