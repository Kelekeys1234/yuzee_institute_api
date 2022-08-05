package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CoursePrerequisite;

@Repository
public interface CoursePrerequisiteRepository extends MongoRepository<CoursePrerequisite, String> {

	public List<CoursePrerequisite> findByCourseId(String courseId);

}
