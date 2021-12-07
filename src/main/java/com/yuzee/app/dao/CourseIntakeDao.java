package com.yuzee.app.dao;

import java.util.List;

public interface CourseIntakeDao {
	void deleteByCourseIdIn(List<String> ids);
}
