package com.seeka.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Institute;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, String>{

	public Page<Institute> findByCountryName(String countryName, @PageableDefault Pageable pageable);
}
