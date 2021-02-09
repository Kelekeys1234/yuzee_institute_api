package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJob;

@Repository
public interface CareerJobRepository extends JpaRepository<CareerJob, String> {

	public Page<CareerJob> findByIdIn(List<String> jobIds, Pageable pageable);
	
	public Page<CareerJob> findByJobContainingIgnoreCase(String name, Pageable pageable);
}
