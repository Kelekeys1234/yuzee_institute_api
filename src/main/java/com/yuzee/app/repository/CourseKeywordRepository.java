package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseKeywords;

@Repository
public interface CourseKeywordRepository extends MongoRepository<CourseKeywords, String> {

	public List<CourseKeywords> findByKeywordContaining(String keyword);

}
