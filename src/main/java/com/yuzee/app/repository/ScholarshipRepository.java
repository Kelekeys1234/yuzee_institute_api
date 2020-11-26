package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Scholarship;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, String> {

	public Long countByInstituteId(String instituteId);
}
