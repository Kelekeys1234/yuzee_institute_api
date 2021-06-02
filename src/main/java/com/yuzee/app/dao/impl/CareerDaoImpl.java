package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.Careers;
import com.yuzee.app.dao.CareerDao;
import com.yuzee.app.repository.CareerRepository;

@Component
public class CareerDaoImpl implements CareerDao {

	@Autowired
	private CareerRepository careerRepository;

	@Override
	public Page<Careers> findByNameContainingIgnoreCase(String name, Pageable pageable) {
		return careerRepository.findByCareerContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<Careers> findByIdIn(List<String> ids) {
		return careerRepository.findAllById(ids);
	}
}
