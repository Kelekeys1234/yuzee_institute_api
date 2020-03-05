package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.UserMyCourse;

public interface IUserMyCourseDAO {
	void save(UserMyCourse reviewObj);

	void update(UserMyCourse reviewObj);

	UserMyCourse get(String userId);

	UserMyCourse getDataByUserIDAndCourseID(String userId, String courseId);

	List<UserMyCourse> getDataByUserID(String userId);

	List<String> getAllCourseIdsByUser(String userId);
}
