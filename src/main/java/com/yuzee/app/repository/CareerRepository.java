package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Careers;

@Repository
public interface CareerRepository extends MongoRepository<Careers, String> {
	Page<Careers> findByCareerContainingIgnoreCase(String career, Pageable pageable);

	List<Careers> findAllById(List<String> id);
}
