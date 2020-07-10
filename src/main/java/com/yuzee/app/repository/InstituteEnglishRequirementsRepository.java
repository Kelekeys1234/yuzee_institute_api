package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteEnglishRequirements;

@Repository
public interface InstituteEnglishRequirementsRepository extends JpaRepository<InstituteEnglishRequirements, String> {
	
	public List<InstituteEnglishRequirements> findByInstituteId (String instituteId);

}
