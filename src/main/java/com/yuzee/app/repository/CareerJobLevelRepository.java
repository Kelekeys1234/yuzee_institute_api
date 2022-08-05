package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobLevel;

@Repository
public interface CareerJobLevelRepository extends MongoRepository<CareerJobLevel, String> {

}
