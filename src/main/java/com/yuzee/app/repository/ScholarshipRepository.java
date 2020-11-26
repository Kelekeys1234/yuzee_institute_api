package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Scholarship;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, String> {

	public Long countByInstituteId(String instituteId);
	
	public List<Scholarship> findByIdIn(List<String> scholarshipIds);
	
}
