package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CoursePayment;

@Repository
public interface CoursePaymentRepository extends JpaRepository<CoursePayment, String> {

}
