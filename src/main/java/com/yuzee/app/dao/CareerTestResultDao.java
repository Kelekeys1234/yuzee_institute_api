package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CareerTestResult;
import com.yuzee.app.enumeration.CareerTestEntityType;

public interface CareerTestResultDao {
	void saveAll(List<CareerTestResult> careerTestResults);

	List<CareerTestResult> findByUserIdAndEntityType(String userId, CareerTestEntityType entityType);

	void deleteAll(List<CareerTestResult> enities);
}
