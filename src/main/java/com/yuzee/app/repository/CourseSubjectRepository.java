package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseSubject;

@Repository
public interface CourseSubjectRepository extends JpaRepository<CourseSubject, String> {
	public void deleteByCourseId(String courseId);
}
