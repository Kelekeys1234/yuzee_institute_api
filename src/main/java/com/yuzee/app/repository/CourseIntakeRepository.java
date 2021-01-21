package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuzee.app.bean.CourseIntake;

public interface CourseIntakeRepository extends JpaRepository<CourseIntake, String> {
	List<CourseIntake> findByCourseIdAndIdIn(String courseId, List<String> ids);

	void deleteByIdIn(List<String> ids);
}
