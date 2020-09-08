package com.yuzee.app.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.CareerJob;
import com.yuzee.app.bean.CareerJobCourseSearchKeyword;
import com.yuzee.app.bean.CareerJobSkill;
import com.yuzee.app.bean.CareerJobSubject;
import com.yuzee.app.bean.CareerJobWorkingStyle;

public interface CareerTestDao {

	public List<CareerJobSkill> getCareerJobSkills(String levelId, Integer startIndex, Integer pageSize);
	
	public Integer getCareerJobSkillCount(String levelId);
	
	public List<CareerJobWorkingStyle> getCareerJobWorkingStyle(List<String> jobIds, Pageable pageable);
	
	public Integer getCareerJobWorkingStyleCount(List<String> jobIds);
	
	public List<CareerJobSubject> getCareerJobSubject(List<String> jobIds, Pageable pageable);
	
	public Integer getCareerJobSubjectCount(List<String> jobIds);
	
	public List<CareerJob> getCareerJob(List<String> jobIds, Pageable pageable);
	
	public Integer getCareerJobCount(List<String> jobIds);
	
	public List<CareerJobCourseSearchKeyword> getCareerJobCourseSearchKeyword(List<String> jobIds);
}
