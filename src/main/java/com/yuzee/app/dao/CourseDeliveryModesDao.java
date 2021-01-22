package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseDeliveryModes;
import com.yuzee.app.exception.ValidationException;

public interface CourseDeliveryModesDao {

	List<CourseDeliveryModes> saveAll(List<CourseDeliveryModes> courseDeliveryModes) throws ValidationException;

	public List<CourseDeliveryModes> getCourseDeliveryModesByCourseId(String courseId);

	void deleteByCourseIdAndIdIn(String courseId, List<String> ids);

	List<CourseDeliveryModes> findByCourseIdAndIdIn(String courseId, List<String> ids);
}
