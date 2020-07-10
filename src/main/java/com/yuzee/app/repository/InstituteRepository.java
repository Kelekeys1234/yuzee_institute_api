package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Institute;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, String>{

	public Page<Institute> findByCountryName(String countryName, @PageableDefault Pageable pageable);
	
	@Query("SELECT COUNT(*) FROM Institute i where i.countryName = :countryName")
	public Integer getTotalCountOfInstituteByCountryName(String countryName);
	
	public List<Institute> findByCityName(String cityName);
}
