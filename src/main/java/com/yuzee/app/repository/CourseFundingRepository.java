package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuzee.app.bean.CourseFunding;

public interface CourseFundingRepository extends JpaRepository<CourseFunding, String> {

	List<CourseFunding> findByCourseIdAndFundingNameIdIn(String courseId, List<String> fundingNameIds);

	void deleteByCourseIdAndFundingNameIdIn(String courseId, List<String> fundingNameIds);
}
