package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.ScholarshipEligibleNationality;

@Repository
public interface ScholarshipEligibleNationalityRepository extends JpaRepository<ScholarshipEligibleNationality, String> {

	public List<ScholarshipEligibleNationality> findByScholarshipId(String scholarshipId);
	
}
