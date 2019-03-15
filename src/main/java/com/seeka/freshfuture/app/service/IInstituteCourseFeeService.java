package com.seeka.freshfuture.app.service;

import com.seeka.freshfuture.app.bean.InstituteCourseFee;

public interface IInstituteCourseFeeService {
	
	public void save(InstituteCourseFee obj);
	public void update(InstituteCourseFee obj);
	public InstituteCourseFee get(Integer id);
	public InstituteCourseFee getCourseFeeByInstitueAndCourse(Integer instituteId, Integer courseId);
}
