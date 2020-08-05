package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CareerJobType;

@Repository
public interface CareerJobTypeRepository extends JpaRepository<CareerJobType, String>{

}
