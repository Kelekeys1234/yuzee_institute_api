package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CareerJob;
import com.yuzee.app.bean.CareerJobCourseSearchKeyword;
import com.yuzee.app.bean.CareerJobSkill;
import com.yuzee.app.bean.CareerJobSubject;
import com.yuzee.app.bean.CareerJobType;
import com.yuzee.app.bean.CareerJobWorkingStyle;
import com.yuzee.app.bean.RelatedCareer;
import com.yuzee.app.dao.CareerTestDao;
import com.yuzee.app.dto.JobIdProjection;
import com.yuzee.app.repository.CareerJobCourseSearchKeywordRepository;
import com.yuzee.app.repository.CareerJobRepository;
import com.yuzee.app.repository.CareerJobSkillRepository;
import com.yuzee.app.repository.CareerJobSubjectRepository;
import com.yuzee.app.repository.CareerJobTypeRepository;
import com.yuzee.app.repository.CareerJobWorkingStyleRepository;
import com.yuzee.app.repository.RelatedCareerRepository;

@Component
public class CareerTestDaoImpl implements CareerTestDao {

	@Autowired
	private CareerJobWorkingStyleRepository careerJobWorkingStyleRepository;

	@Autowired
	private CareerJobSubjectRepository careerJobSubjectRepository;

	@Autowired
	private CareerJobTypeRepository careerJobTypeRepository;

	@Autowired
	private CareerJobRepository careerJobRepository;

	@Autowired
	private RelatedCareerRepository relatedCareerRepository;

	@Autowired
	private CareerJobSkillRepository careerJobSkillRepository;

	@Autowired
	private CareerJobCourseSearchKeywordRepository careerJobCourseSearchKeywordRepository;

	@Override
	public Page<CareerJobSkill> getCareerJobSkills(String levelId, String jobId, Pageable pageable) {
		return careerJobSkillRepository.findByLevelIdAndJobId(levelId, jobId, pageable);
	}

	@Override
	public Page<CareerJobWorkingStyle> getCareerJobWorkingStyle(List<String> jobIds, Pageable pageable) {
		return careerJobWorkingStyleRepository.findByCareerJobsIdIn(jobIds, pageable);
	}

	@Override
	public Page<CareerJobSubject> getCareerJobSubject(List<String> jobIds, Pageable pageable) {
		return careerJobSubjectRepository.findByCareerJobsIdIn(jobIds, pageable);
	}

	@Override
	public Page<CareerJobType> getCareerJobType(List<String> jobIds, Pageable pageable) {
		return careerJobTypeRepository.findByCareerJobsIdIn(jobIds, pageable);
	}
	
	@Override
	public List<JobIdProjection> getCareerJobIdsByJobTypeId(String jobTypeId){
		return careerJobTypeRepository.findJobIdsById(jobTypeId);
	}

	@Override
	public Page<CareerJob> getCareerJob(List<String> jobIds, Pageable pageable) {
		return careerJobRepository.findByIdIn(jobIds, pageable);
	}

	@Override
	public Page<RelatedCareer> getRelatedCareers(List<String> carrerIds, Pageable pageable) {
		return relatedCareerRepository.findByCareersIdIn(carrerIds, pageable);
	}

	@Override
	public List<CareerJobCourseSearchKeyword> getCareerJobCourseSearchKeyword(List<String> jobIds) {
		return careerJobCourseSearchKeywordRepository.findByCareerJobsIdIn(jobIds);
	}

	@Override
	public Optional<CareerJob> getCareerJob(String careerJobId) {
		return careerJobRepository.findById(careerJobId);
	}

	@Override
	public Page<CareerJob> getCareerJobByName(String name, Pageable pageable) {
		return careerJobRepository.findByJobContainingIgnoreCase(name, pageable);
	}
	
	@Override
	public Page<CareerJobSkill> getJobSkills(List<String> jobNames, Pageable pageable) {
		return careerJobSkillRepository.findByCareerJobs_JobIn(jobNames, pageable);
	}
}
