package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserMyCourse;
import com.seeka.app.dto.UserMyCourseRequestDto;
import com.seeka.app.exception.ValidationException;

/**
 *
 * @author SeekADegree
 *
 */
public interface IUserMyCourseService {
	void save(UserMyCourse user);

	void update(UserMyCourse user);

	UserMyCourse get(BigInteger userId);

	UserMyCourse getDataByUserIDAndCourseID(BigInteger userId, BigInteger courseId);

	List<UserMyCourse> getUserMyCourseByUserID(BigInteger userId);

	List<BigInteger> getAllCourseIdsByUser(BigInteger userId);

	void createUserMyCourse(UserMyCourseRequestDto courseRequestDto) throws ValidationException;
}
