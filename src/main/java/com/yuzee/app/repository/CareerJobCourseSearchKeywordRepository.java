package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobCourseSearchKeyword;

@Repository
public interface CareerJobCourseSearchKeywordRepository extends MongoRepository<CareerJobCourseSearchKeyword, String> {

	public List<CareerJobCourseSearchKeyword> findByCareerJobsIdInOrderByCourseSearchKeyword(List<String> jobIds);
}
