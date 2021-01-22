package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseDeliveryModes;

@Repository
public interface CourseDeliveryModesRepository extends JpaRepository<CourseDeliveryModes, String> {

	public List<CourseDeliveryModes> findByCourseId(String courseId);

	public void deleteByIdIn(List<String> ids);
}
