package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobSubject;

@Repository
public interface CareerJobSubjectRepository extends MongoRepository<CareerJobSubject, String> {

	public Page<CareerJobSubject> findByCareerJobsIdInOrderBySubject(List<String> jobIds, Pageable pageable);
}
