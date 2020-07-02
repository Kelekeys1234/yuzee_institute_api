package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.CourseDeliveryModes;

public interface CourseDeliveryModesDao {

	public void saveCourseAdditionalInfo(CourseDeliveryModes courseAdditionalInfo);
	
	public void deleteCourseAdditionalInfo(CourseDeliveryModes courseAdditionalInfo);
	
	public List<CourseDeliveryModes> getCourseAdditionalInfoByCourseId(String courseId);
}
