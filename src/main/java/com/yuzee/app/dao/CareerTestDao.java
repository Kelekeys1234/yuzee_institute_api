package com.yuzee.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.CareerJob;
import com.yuzee.app.bean.CareerJobCourseSearchKeyword;
import com.yuzee.app.bean.CareerJobSkill;
import com.yuzee.app.bean.CareerJobSubject;
import com.yuzee.app.bean.CareerJobType;
import com.yuzee.app.bean.CareerJobWorkingStyle;
import com.yuzee.app.bean.RelatedCareer;
import com.yuzee.app.dto.JobIdProjection;

public interface CareerTestDao {

	public Page<CareerJobSkill> getCareerJobSkills(String levelId, Pageable pageable);
	
	public Page<CareerJobWorkingStyle> getCareerJobWorkingStyle(List<String> jobIds, Pageable pageable);
	
	public Page<CareerJobSubject> getCareerJobSubject(List<String> jobIds, Pageable pageable);
	
	public Page<CareerJobType> getCareerJobType(List<String> jobIds, Pageable pageable);

	public List<JobIdProjection> getCareerJobIdsByJobTypeId(String jobTypeId);
		
	public Page<CareerJob> getCareerJob(List<String> jobIds, Pageable pageable);
	
	public Page<CareerJob> getCareerJobByName(String name, Pageable pageable);
	
	public Page<RelatedCareer> getRelatedCareers(List<String> carrerIds, Pageable pageable);
	
	public List<CareerJobCourseSearchKeyword> getCareerJobCourseSearchKeyword(List<String> jobIds);
	
	public Optional<CareerJob> getCareerJob(String careerJobId); 
}
