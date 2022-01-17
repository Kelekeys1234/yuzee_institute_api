package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseIntake;

@Repository
public interface CourseIntakeRepository extends JpaRepository<CourseIntake, String> {

}
