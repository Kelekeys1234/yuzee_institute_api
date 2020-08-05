package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.RelatedCareer;

@Repository
public interface RelatedCareerRepository extends JpaRepository<RelatedCareer, String> {

}
