package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuzee.app.bean.CourseContactPerson;

public interface CourseContactPersonRepository extends JpaRepository<CourseContactPerson, String> {

	List<CourseContactPerson> findByCourseIdAndUserIdIn(String courseId, List<String> userIds);

	void deleteByCourseIdAndUserIdIn(String courseId, List<String> userIds);
}
