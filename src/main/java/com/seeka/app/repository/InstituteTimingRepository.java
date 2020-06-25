package com.seeka.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteTiming;

@Repository
public interface InstituteTimingRepository extends JpaRepository<InstituteTiming, String>{

	InstituteTiming findByInstituteId(String instituteId);
	
}
