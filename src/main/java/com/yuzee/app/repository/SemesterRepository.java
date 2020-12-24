package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {
}
