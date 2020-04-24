package com.seeka.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.InstituteService;

@Repository
public interface InstituteServiceRepository extends JpaRepository<InstituteService, String> {

    public List<InstituteService> findByInstituteId (String instituteId);
	
	public void deleteByIdAndInstituteId (String serviceId, String instituteId);

	
}
