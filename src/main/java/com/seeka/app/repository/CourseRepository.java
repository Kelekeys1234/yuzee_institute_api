package com.seeka.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>{
 
	public List<Course> findByInstituteIdAndFacultyIdAndIsActive (String instituteId , String facultyId, boolean isActive);
	
	public List<Course> findByInstituteIdAndFacultyId (String instituteId , String facultyId);
}
