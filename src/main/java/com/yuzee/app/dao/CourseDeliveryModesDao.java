package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseDeliveryModes;

public interface CourseDeliveryModesDao {

	public void saveCourseDeliveryModes(CourseDeliveryModes courseDeliveryModes);
	
	public void deleteCourseDeliveryModes(String courseDeliveryModeId);
	
	public List<CourseDeliveryModes> getCourseDeliveryModesByCourseId(String courseId);
}
