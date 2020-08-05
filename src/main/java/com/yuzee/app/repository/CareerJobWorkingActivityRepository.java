package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobWorkingActivity;

@Repository
public interface CareerJobWorkingActivityRepository extends JpaRepository<CareerJobWorkingActivity, String>{

}
