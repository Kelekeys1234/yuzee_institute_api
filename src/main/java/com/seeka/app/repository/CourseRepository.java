package com.seeka.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>{
 
	public List<Course> findByInstituteIdAndFacultyIdAndIsActive (String instituteId , String facultyId, boolean isActive);
	
	public List<Course> findByInstituteIdAndFacultyId (String instituteId , String facultyId);
	
	public Page<Course> findByInstituteId (@PageableDefault Pageable pageable, String instituteId);
	
	@Query("SELECT COUNT(*) from Course c where c.institute.id = :instituteId")
	public long getTotalCountOfCourseByInstituteId (String instituteId);
	
	@Query("SELECT COUNT(*) FROM Course c INNER JOIN Institute i on i.id = c.institute.id where i.countryName = :countryName")
	public long getTotalCountOfCourseByCountryName (String countryName);
}
