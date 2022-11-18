package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.HelpCategory;

@Repository
public interface HelpCategoryRepository extends MongoRepository<HelpCategory,String>{
}
