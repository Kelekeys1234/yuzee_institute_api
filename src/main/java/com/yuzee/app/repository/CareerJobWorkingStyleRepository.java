package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobWorkingStyle;

@Repository
public interface CareerJobWorkingStyleRepository extends MongoRepository<CareerJobWorkingStyle, String> {

	public Page<CareerJobWorkingStyle> findByCareerJobsIdInOrderByWorkStyle(List<String> jobIds, Pageable pageable);
}
