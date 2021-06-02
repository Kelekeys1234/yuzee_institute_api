package com.yuzee.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CoursePayment;
import com.yuzee.app.dao.CoursePaymentDao;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.app.repository.CoursePaymentRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CoursePaymentDaoImpl implements CoursePaymentDao {

	@Autowired
	private CoursePaymentRepository coursePaymentRepository;

	@Override
	public CoursePayment save(CoursePayment coursePayment) throws ValidationException {
		try {
			return coursePaymentRepository.save(coursePayment);
		} catch (DataIntegrityViolationException e) {
			log.error("one or more payment_items already exists with same name");
			throw new ValidationException("one or more payment_items already exists with same name");
		}
	}

	@Override
	public void delete(CoursePayment coursePayment) {
		coursePaymentRepository.delete(coursePayment);
	}
}
