package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobCourseSearchKeyword;

@Repository
public interface CareerJobCourseSearchKeywordRepository extends JpaRepository<CareerJobCourseSearchKeyword, String> {

	public List<CareerJobCourseSearchKeyword> findByCareerJobsIdInOrderByCourseSearchKeyword(List<String> jobIds);
}
