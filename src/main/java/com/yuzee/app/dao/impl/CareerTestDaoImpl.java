package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.CareerJob;
import com.yuzee.app.bean.CareerJobCourseSearchKeyword;
import com.yuzee.app.bean.CareerJobSkill;
import com.yuzee.app.bean.CareerJobSubject;
import com.yuzee.app.bean.CareerJobType;
import com.yuzee.app.bean.CareerJobWorkingStyle;
import com.yuzee.app.bean.RelatedCareer;
import com.yuzee.app.dao.CareerTestDao;
import com.yuzee.app.repository.CareerJobCourseSearchKeywordRepository;
import com.yuzee.app.repository.CareerJobRepository;
import com.yuzee.app.repository.CareerJobSkillRepository;
import com.yuzee.app.repository.CareerJobSubjectRepository;
import com.yuzee.app.repository.CareerJobTypeRepository;
import com.yuzee.app.repository.CareerJobWorkingStyleRepository;
import com.yuzee.app.repository.RelatedCareerRepository;

@Component
@Transactional
public class CareerTestDaoImpl implements CareerTestDao {

	@PersistenceContext
	private EntityManager entityManager;

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
	public Page<CareerJobSkill> getCareerJobSkills(String levelId, Pageable pageable) {
		return careerJobSkillRepository.findByLevelId(levelId, pageable);
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
}
