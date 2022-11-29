package com.yuzee.app.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobSkill;

@Repository
public interface CareerJobSkillRepository extends MongoRepository<CareerJobSkill, String> {

	@org.springframework.data.mongodb.repository.Query("SELECT distinct JS from CareerJobSkill JS LEFT JOIN CareerJobLevel JL on JL.careerJobs.id = JS.careerJobs.id "
			+ "where (null = :levelId or JL.level.id = :levelId) and (null = :jobId or JS.careerJobs.id = :jobId) order by JS.skill ")
	public Page<CareerJobSkill> findByLevelIdAndJobId(String levelId, String jobId, Pageable pageable);

	@org.springframework.data.mongodb.repository.Query("SELECT distinct JS from CareerJobSkill JS " + "where (null = :jobNames or JS.careerJobs.job in :jobNames) ")
	public Page<CareerJobSkill> findByCareerJobs_JobIn(List<String> jobNames, Pageable pageable);
}
