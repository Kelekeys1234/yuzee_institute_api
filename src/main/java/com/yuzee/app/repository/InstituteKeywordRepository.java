package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteKeywords;

@Repository
public interface InstituteKeywordRepository extends MongoRepository<InstituteKeywords,String>{

}
