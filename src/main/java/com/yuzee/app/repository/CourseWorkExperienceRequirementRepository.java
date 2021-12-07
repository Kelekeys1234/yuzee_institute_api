package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseWorkExperienceRequirement;

@Repository
public interface CourseWorkExperienceRequirementRepository extends JpaRepository<CourseWorkExperienceRequirement, String> {

}
