package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobSkill;

@Repository
public interface CareerJobSkillRepository extends JpaRepository<CareerJobSkill, String> {

	@Query("SELECT distinct JS from CareerJobSkill JS LEFT JOIN CareerJobLevel JL on JL.careerJobs.id = JS.careerJobs.id "
			+ "where (null = :levelId or JL.level.id = :levelId) and (null = :jobId or JS.careerJobs.id = :jobId) ")
	public Page<CareerJobSkill> findByLevelIdAndJobId(String levelId, String jobId, Pageable pageable);

	public Page<CareerJobSkill> findByCareerJobsIdIn(List<String> jobIds, Pageable pageable);
}
