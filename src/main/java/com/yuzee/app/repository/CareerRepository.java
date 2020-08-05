package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Careers;

@Repository
public interface CareerRepository extends JpaRepository<Careers, String>{

}
