package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseWorkPlacementRequirement;

@Repository
public interface CourseWorkPlacementRequirementRepository extends JpaRepository<CourseWorkPlacementRequirement, String> {

}
