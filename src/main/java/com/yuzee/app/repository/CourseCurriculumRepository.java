package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseCurriculum;

@Repository
public interface CourseCurriculumRepository extends MongoRepository<CourseCurriculum, String> {

}
