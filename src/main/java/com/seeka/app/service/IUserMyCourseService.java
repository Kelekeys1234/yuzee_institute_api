package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.UserMyCourse;
import com.seeka.app.dto.UserCourseRequestDto;
import com.seeka.app.exception.ValidationException;

/**
 *
 * @author SeekADegree
 *
 */
public interface IUserMyCourseService {
	void save(UserMyCourse user);

	void update(UserMyCourse user);

	UserMyCourse get(String userId);

	UserMyCourse getDataByUserIDAndCourseID(String userId, String courseId);

	List<UserMyCourse> getUserMyCourseByUserID(String userId);

	List<String> getAllCourseIdsByUser(String userId);

	void createUserMyCourse(UserCourseRequestDto courseRequestDto) throws ValidationException;
}
