package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseDeliveryModes;

@Repository
public interface CourseDeliveryModesRepository extends MongoRepository<CourseDeliveryModes, String> {

	public List<CourseDeliveryModes> findByCourseId(String courseId);
}
