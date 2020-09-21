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

	@Query("SELECT JS from CareerJobLevel JL LEFT JOIN CareerJobSkill JS on JL.careerJobs.id = JS.careerJobs.id "
			+ "where JL.level.id = :levelId ")
	public Page<CareerJobSkill> findByLevelId(String levelId, Pageable pageable);

	public Page<CareerJobSkill> findByCareerJobsIdIn(List<String> jobIds, Pageable pageable);
}
