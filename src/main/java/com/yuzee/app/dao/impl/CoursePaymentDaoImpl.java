package com.yuzee.app.dao.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CoursePayment;
import com.yuzee.app.dao.CoursePaymentDao;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.local.config.MessageTranslator;
import com.yuzee.app.repository.CoursePaymentRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CoursePaymentDaoImpl implements CoursePaymentDao {

	@Autowired
	private CoursePaymentRepository coursePaymentRepository;

	@Autowired
	private MessageTranslator messageTranslator;
	
	@Override
	public CoursePayment save(CoursePayment coursePayment) throws ValidationException {
		try {
			return coursePaymentRepository.save(coursePayment);
		} catch (DataIntegrityViolationException e) {
			log.error(messageTranslator.toLocale("course-payment.already.name_exist",Locale.US));
			throw new ValidationException(messageTranslator.toLocale("course-payment.already.name_exist"));
		}
	}

	@Override
	public void delete(CoursePayment coursePayment) {
		coursePaymentRepository.delete(coursePayment);
	}
}
