package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CareerTestResult;
import com.yuzee.app.dao.CareerTestResultDao;
import com.yuzee.app.enumeration.CareerTestEntityType;
import com.yuzee.app.repository.CareerTestResultRepository;

@Component
public class CareerTestResultDaoImpl implements CareerTestResultDao {

	@Autowired
	private CareerTestResultRepository careerTestResultRepository;

	@Override
	public void saveAll(List<CareerTestResult> careerTestResults) {
		careerTestResultRepository.saveAll(careerTestResults);
	}

	@Override
	public List<CareerTestResult> findByUserIdAndEntityType(String userId, CareerTestEntityType entityType) {
		return careerTestResultRepository.findByUserIdAndEntityType(userId, entityType);
	}

	@Override
	public void deleteAll(List<CareerTestResult> enities) {
		careerTestResultRepository.deleteAll(enities);
	}

}
