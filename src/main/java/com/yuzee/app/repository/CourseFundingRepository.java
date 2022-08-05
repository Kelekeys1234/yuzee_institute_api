package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yuzee.app.bean.CourseFunding;

public interface CourseFundingRepository extends MongoRepository<CourseFunding, String> {
}
