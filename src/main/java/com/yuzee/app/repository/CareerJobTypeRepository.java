package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobType;

@Repository
public interface CareerJobTypeRepository extends JpaRepository<CareerJobType, String> {
	
	public Page<CareerJobType> findByCareerJobsIdIn(List<String> jobId, Pageable pageable);

}
