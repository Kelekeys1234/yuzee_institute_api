package com.yuzee.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.OffCampusCourse;

@Repository
public interface OffCampusCourseRepository extends MongoRepository<OffCampusCourse, String> {
	public Page<OffCampusCourse> findByCourseInstituteId(String instituteId, Pageable pageable);
}