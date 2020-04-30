package com.seeka.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Institute;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, String>{

}
