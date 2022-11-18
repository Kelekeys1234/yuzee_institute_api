package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.HelpSubCategory;

@Repository
public interface HelpSubCategoryRepository extends MongoRepository<HelpSubCategory,String>{
}
