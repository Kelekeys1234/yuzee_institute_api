package com.yuzee.app.dao;

import com.yuzee.app.bean.CareerJobCourseSearchKeyword;
import com.yuzee.app.bean.CareerJobLevel;
import com.yuzee.app.bean.CareerJobSkill;
import com.yuzee.app.bean.CareerJobSubject;
import com.yuzee.app.bean.CareerJobType;
import com.yuzee.app.bean.CareerJobWorkingActivity;
import com.yuzee.app.bean.CareerJobWorkingStyle;
import com.yuzee.app.bean.CareerJob;
import com.yuzee.app.bean.Careers;
import com.yuzee.app.bean.RelatedCareer;

public interface CareerUploadDao {

	public void saveCareerList(Careers careerList);
	
	public void saveRelatedCareers(RelatedCareer relatedCareer);
	
	public void saveJobsData(CareerJob careerJobs);
	
	public void saveJobsCourseSearchKeyword(CareerJobCourseSearchKeyword careerJobCourseSearchKeyword);
	
	public void saveJobsLevel(CareerJobLevel careerJobLevel);
	
	public void saveJobsSkill(CareerJobSkill careerJobSkill);
	
	public void saveJobSubject(CareerJobSubject careerJobSubject);
	
	public void saveJobType(CareerJobType careerJobType);
	
	public void saveJobWorkingActivity(CareerJobWorkingActivity careerJobWorkingActivity);
	
	public void saveJobWorkingStyle(CareerJobWorkingStyle careerJobWorkingStyle);
	
	public Careers getCareer(String career);
	
	public RelatedCareer getRelatedCareer(String careerId, String relatedCareer);
	
	public CareerJob getJob(String job);
	
	public CareerJobCourseSearchKeyword getJobCourseSearchKeyword(String jobId, String courseSearchKeyword);
	
	public CareerJobLevel getJobLevel(String jobId, String levelId);
	
	public CareerJobSkill getJobSkill(String jobId, String skillName);
	
	public CareerJobSubject getJobSubject(String jobId, String subjectName);
	
	public CareerJobType getJobType(String jobId, String jobType);
	
	public CareerJobWorkingActivity getJobWorkingActivity(String jobId, String workingActivity);
	
	public CareerJobWorkingStyle getJobWorkingStyle(String jobId, String workingStyle);
}
