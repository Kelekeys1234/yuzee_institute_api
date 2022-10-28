package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.RelatedCareer;

@Repository
public interface RelatedCareerRepository extends MongoRepository<RelatedCareer, String> {
	Page<RelatedCareer> findByCareersIdInOrderByRelatedCareer(List<String> careerIds, Pageable pageable);
}
