package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseInstitute;

@Repository
public interface CourseInstituteRepository extends MongoRepository<CourseInstitute, String> {

	@Query("select courseInstitute from CourseInstitute courseInstitute where courseInstitute.id in "
			+ "( select id from CourseInstitute ci where ci.sourceCourse.id = :courseId or ci.destinationCourse.id = :courseId )"
			+ " OR " + " courseInstitute.id in (select id from CourseInstitute ci where ci.sourceCourse in"
			+ " (select sourceCourse from CourseInstitute ciSub where ciSub.destinationCourse.id = :courseId))")
	List<CourseInstitute> getById(String courseId);

	CourseInstitute findByDestinationCourseId(String destinationCourseId);
}
