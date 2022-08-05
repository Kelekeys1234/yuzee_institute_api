package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseIntake;

@Repository
public interface CourseIntakeRepository extends MongoRepository<CourseIntake, String> {

}
