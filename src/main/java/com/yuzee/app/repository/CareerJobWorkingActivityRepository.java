package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobWorkingActivity;

@Repository
public interface CareerJobWorkingActivityRepository extends MongoRepository<CareerJobWorkingActivity, String> {

}
