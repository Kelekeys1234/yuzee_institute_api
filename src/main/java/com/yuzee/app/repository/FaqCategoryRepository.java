package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.FaqCategory;

@Repository
public interface FaqCategoryRepository extends MongoRepository<FaqCategory, String> {
}
