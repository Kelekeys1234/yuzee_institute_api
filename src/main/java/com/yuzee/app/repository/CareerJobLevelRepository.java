package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobLevel;

@Repository
public interface CareerJobLevelRepository extends JpaRepository<CareerJobLevel, String> {

}
