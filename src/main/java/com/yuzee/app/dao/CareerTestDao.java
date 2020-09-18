package com.yuzee.app.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.CareerJob;
import com.yuzee.app.bean.CareerJobCourseSearchKeyword;
import com.yuzee.app.bean.CareerJobSkill;
import com.yuzee.app.bean.CareerJobSubject;
import com.yuzee.app.bean.CareerJobType;
import com.yuzee.app.bean.CareerJobWorkingStyle;
import com.yuzee.app.bean.Careers;
import com.yuzee.app.bean.RelatedCareer;

public interface CareerTestDao {

	public List<CareerJobSkill> getCareerJobSkills(String levelId, Integer startIndex, Integer pageSize);
	
	public Integer getCareerJobSkillCount(String levelId);
	
	public List<CareerJobWorkingStyle> getCareerJobWorkingStyle(List<String> jobIds, Pageable pageable);
	
	public Integer getCareerJobWorkingStyleCount(List<String> jobIds);
	
	public List<CareerJobSubject> getCareerJobSubject(List<String> jobIds, Pageable pageable);
	
	public Integer getCareerJobSubjectCount(List<String> jobIds);
	
	public Page<CareerJobType> getCareerJobType(List<String> jobIds, Pageable pageable);
	
	public Page<Careers> getCareers(List<String> jobTypeIds, Pageable pageable);
	
	public List<CareerJob> getCareerJob(List<String> jobIds, Pageable pageable);

	public Integer getCareerJobCount(List<String> jobIds);
	
	public Page<RelatedCareer> getRelatedCareers(List<String> carrerIds, Pageable pageable);
	
	public List<CareerJobCourseSearchKeyword> getCareerJobCourseSearchKeyword(List<String> jobIds);
}
