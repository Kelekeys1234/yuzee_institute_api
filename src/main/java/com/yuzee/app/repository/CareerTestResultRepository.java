package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerTestResult;
import com.yuzee.app.enumeration.CareerTestEntityType;

@Repository
public interface CareerTestResultRepository extends MongoRepository<CareerTestResult, String> {

	public List<CareerTestResult> findByUserIdAndEntityType(String userId, CareerTestEntityType entityType);
}
