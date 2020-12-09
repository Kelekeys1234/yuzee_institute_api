package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteService;

@Repository
public interface InstituteServiceRepository extends JpaRepository<InstituteService, String> {

	public List<InstituteService> findByInstituteId(String instituteId);

	public InstituteService findByInstituteIdAndServiceId(String instituteId, String serviceId);
}
