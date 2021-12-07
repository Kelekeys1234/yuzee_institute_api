package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseIntake;

@Repository
public interface CourseIntakeRepository extends JpaRepository<CourseIntake, String> {
	void deleteByCourseIdIn(List<String> courseIds);
}
