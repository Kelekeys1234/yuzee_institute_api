package com.yuzee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.FaqCategory;

@Repository
public interface FaqCategoryRepository extends JpaRepository<FaqCategory, String> {
}
