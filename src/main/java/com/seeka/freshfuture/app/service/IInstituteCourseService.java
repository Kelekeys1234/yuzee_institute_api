package com.seeka.freshfuture.app.service;

import java.util.List;

import com.seeka.freshfuture.app.bean.Institute;
import com.seeka.freshfuture.app.bean.InstituteCourse;

public interface IInstituteCourseService {
	
	public void save(InstituteCourse obj);
	public void update(InstituteCourse obj);
	public InstituteCourse get(Integer id);
	public List<InstituteCourse> getAllCoursesByInstitute(Integer instituteId);
	public InstituteCourse getCourseByInstitueAndCourse(Integer instituteId, Integer courseId);
}
