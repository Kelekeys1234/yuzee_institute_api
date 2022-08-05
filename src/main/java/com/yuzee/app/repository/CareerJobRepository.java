package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJob;

@Repository
public interface CareerJobRepository extends MongoRepository<CareerJob, String> {

	public Page<CareerJob> findByIdIn(List<String> jobIds, Pageable pageable);

	public Page<CareerJob> findByJobContainingIgnoreCaseOrderByJob(String name, Pageable pageable);
}
