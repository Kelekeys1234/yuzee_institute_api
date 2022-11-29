package com.yuzee.app.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobType;
import com.yuzee.app.dto.JobIdProjection;

@Repository
public interface CareerJobTypeRepository extends MongoRepository<CareerJobType, String> {

	public Page<CareerJobType> findByCareerJobsIdInOrderByJobType(List<String> jobId, Pageable pageable);

	@org.springframework.data.mongodb.repository.Query("select j.id as jobId from CareerJobType jt join jt.careerJobs j where jt.id = :id")
	public List<JobIdProjection> findJobIdsById(String id);
}
