package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuzee.app.bean.CourseFunding;

public interface CourseFundingRepository extends JpaRepository<CourseFunding, String> {
}
