package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CoursePrerequisiteSubjects;

@Repository
public interface CoursePrerequisiteSubjectRepository extends MongoRepository<CoursePrerequisiteSubjects, String> {

	public List<CoursePrerequisiteSubjects> findByCoursePrerequisiteId(String coursePrerequisiteId);
}
