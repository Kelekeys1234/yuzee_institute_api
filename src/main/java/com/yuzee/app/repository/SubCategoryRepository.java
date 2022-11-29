package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yuzee.app.bean.SubCategory;

public interface SubCategoryRepository extends MongoRepository<SubCategory, String> {

}
