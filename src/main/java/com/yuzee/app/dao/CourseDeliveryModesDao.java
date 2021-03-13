package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseDeliveryModes;

public interface CourseDeliveryModesDao {

	public List<CourseDeliveryModes> getCourseDeliveryModesByCourseId(String courseId);
}
