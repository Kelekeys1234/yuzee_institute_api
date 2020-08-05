package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobSkill;

@Repository
public interface CareerJobSkillRepository extends JpaRepository<CareerJobSkill, String> {

	public List<CareerJobSkill> findByCareerJobsIdIn(List<String> jobIds, @PageableDefault Pageable pageable);
	
	public long countByCareerJobsIdIn(List<String> jobIds);
}
