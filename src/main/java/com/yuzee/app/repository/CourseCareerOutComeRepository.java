package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseCareerOutcome;

@Repository
public interface CourseCareerOutComeRepository extends JpaRepository<CourseCareerOutcome, String> {

	void deleteByIdIn(List<String> courseCareerOutcomeIds);
}
