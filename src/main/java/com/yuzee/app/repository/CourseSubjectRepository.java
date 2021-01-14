package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuzee.app.bean.CourseSubject;

public interface CourseSubjectRepository extends JpaRepository<CourseSubject, String> {
	void deleteByIdIn(List<String> ids);
}
