package com.yuzee.app.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Course;

@Repository
public interface CourseRepository extends MongoRepository<Course, UUID>{

	public List<Course> findByInstituteIdAndFacultyIdAndIsActive (String instituteId , String facultyId, boolean isActive);

	public List<Course> findByInstituteIdAndFacultyId (String instituteId , String facultyId);

	public List<Course> findByInstituteId(Pageable pageable, String instituteId);

	public List<Course> findByInstituteId(String instituteId);

//	@Query("SELECT COUNT(*) from Course c where c.institute.id = :instituteId")
//	public long getTotalCountOfCourseByInstituteId (String instituteId);
//
//	@Query("SELECT COUNT(*) FROM Course c INNER JOIN Institute i on i.id = c.institute.id where i.countryName = :countryName")
//	public long getTotalCountOfCourseByCountryName (String countryName);

	public List<Course> findByIsActiveAndDeletedOnAndNameContaining(Pageable pageable, Boolean isActive, Date deletedOn, String searchKey);

	public List<Course> findByReadableIdIn(List<String> readableIds);

	public Course findByReadableId(String readableId);
}
