package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseVaccineRequirement;

@Repository
public interface CourseVaccineRequirementRepository extends JpaRepository<CourseVaccineRequirement, String> {

}
