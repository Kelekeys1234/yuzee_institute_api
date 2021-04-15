package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.GradeDetails;

@Repository
public interface GradeDetailRepository extends JpaRepository<GradeDetails, String> {

}
