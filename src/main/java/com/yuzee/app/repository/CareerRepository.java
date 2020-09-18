package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Careers;

@Repository
public interface CareerRepository extends JpaRepository<Careers, String> {
	Page<Careers> findByCareerJobsCareerJobTypesIdIn(List<String> jobTypeIds, Pageable page);
}
