package com.yuzee.app.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
import com.yuzee.app.dao.CareerTestDao;
import com.yuzee.app.repository.CareerJobCourseSearchKeywordRepository;
import com.yuzee.app.repository.CareerJobRepository;
import com.yuzee.app.repository.CareerJobSubjectRepository;
import com.yuzee.app.repository.CareerJobTypeRepository;
import com.yuzee.app.repository.CareerJobWorkingStyleRepository;

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
	private CareerJobCourseSearchKeywordRepository careerJobCourseSearchKeywordRepository;
	
	@Override
	public List<CareerJobSkill> getCareerJobSkills(String levelId, Integer startIndex, Integer pageSize) {
		TypedQuery<CareerJobSkill> query = entityManager.createQuery("SELECT JS from CareerJobLevel JL LEFT JOIN "
				+ "CareerJobSkill JS on JL.careerJobs.id = JS.careerJobs.id where JL.level.id = '"+ levelId + "'", CareerJobSkill.class);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Integer getCareerJobSkillCount(String levelId) {
		TypedQuery<Long> query = entityManager.createQuery("SELECT count(JS.id) from CareerJobLevel JL LEFT JOIN "
				+ "CareerJobSkill JS on JL.careerJobs.id = JS.careerJobs.id where JL.level.id = '"+ levelId + "'", Long.class);
		return query.getSingleResult().intValue();
	}
	
	@Override
	public List<CareerJobWorkingStyle> getCareerJobWorkingStyle(List<String> jobIds, Pageable pageable) {
		return careerJobWorkingStyleRepository.findByCareerJobsIdIn(jobIds, pageable);
	}

	@Override
	public Integer getCareerJobWorkingStyleCount(List<String> jobIds) {
		Long totalCount = careerJobWorkingStyleRepository.countByCareerJobsIdIn(jobIds);
		return totalCount.intValue();
	}

	@Override
	public List<CareerJobSubject> getCareerJobSubject(List<String> jobIds, Pageable pageable) {
		return careerJobSubjectRepository.findByCareerJobsIdIn(jobIds, pageable);
	}

	@Override
	public Integer getCareerJobSubjectCount(List<String> jobIds) {
		Long totalCount = careerJobSubjectRepository.countByCareerJobsIdIn(jobIds);
		return totalCount.intValue();
	}

	@Override
	public Page<CareerJobType> getCareerJobType(List<String> jobIds, Pageable pageable) {
		return careerJobTypeRepository.findByCareerJobsIdIn(jobIds, pageable);
	}

	@Override
	public List<CareerJob> getCareerJob(List<String> jobIds, Pageable pageable) {
		return careerJobRepository.findByIdIn(jobIds, pageable);
	}

	@Override
	public Integer getCareerJobCount(List<String> jobIds) {
		Long totalCount = careerJobRepository.countByIdIn(jobIds);
		return totalCount.intValue();
	}

	@Override
	public List<CareerJobCourseSearchKeyword> getCareerJobCourseSearchKeyword(List<String> jobIds) {
		return careerJobCourseSearchKeywordRepository.findByCareerJobsIdIn(jobIds);
	}
}
