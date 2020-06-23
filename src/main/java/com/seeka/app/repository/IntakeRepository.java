package com.seeka.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Intake;

@Repository
public interface IntakeRepository extends JpaRepository<Intake, String> {

}
