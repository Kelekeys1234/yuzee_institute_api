package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CoursePayment;

@Repository
public interface CoursePaymentRepository extends MongoRepository<CoursePayment, String> {

}
