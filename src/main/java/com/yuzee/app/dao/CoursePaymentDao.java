package com.yuzee.app.dao;

import com.yuzee.app.bean.CoursePayment;
import com.yuzee.app.exception.ValidationException;

public interface CoursePaymentDao {

	CoursePayment save(CoursePayment coursePayment) throws ValidationException;

	void delete(CoursePayment coursePayment);
}
