package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteTiming;

@Repository
public interface InstituteTimingRepository extends JpaRepository<InstituteTiming, String>{

	InstituteTiming findByInstituteId(String instituteId);
	
}
