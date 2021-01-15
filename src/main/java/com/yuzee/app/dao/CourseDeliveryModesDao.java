package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseDeliveryModes;
import com.yuzee.app.exception.ValidationException;

public interface CourseDeliveryModesDao {

	List<CourseDeliveryModes> saveAll(List<CourseDeliveryModes> courseDeliveryModes) throws ValidationException;

	public List<CourseDeliveryModes> getCourseDeliveryModesByCourseId(String courseId);

	void deleteByIdIn(List<String> ids);

	List<CourseDeliveryModes> findByIdIn(List<String> ids);
}
