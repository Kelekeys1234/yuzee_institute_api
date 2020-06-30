package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.CourseAdditionalInfo;

public interface CourseAdditionalInfoDao {

	public void saveCourseAdditionalInfo(CourseAdditionalInfo courseAdditionalInfo);
	
	public void deleteCourseAdditionalInfo(CourseAdditionalInfo courseAdditionalInfo);
	
	public List<CourseAdditionalInfo> getCourseAdditionalInfoByCourseId(String courseId);
}
