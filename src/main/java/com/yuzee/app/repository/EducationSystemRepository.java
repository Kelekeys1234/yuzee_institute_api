package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.EducationSystem;

@Repository
public interface EducationSystemRepository extends JpaRepository<EducationSystem, String> {

	EducationSystem findByNameAndCountryNameAndStateName(String name, String countryName, String stateName);
}
