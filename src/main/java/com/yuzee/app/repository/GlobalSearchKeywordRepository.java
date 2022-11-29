package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yuzee.app.bean.GlobalSearchKeyword;

public interface GlobalSearchKeywordRepository extends MongoRepository<GlobalSearchKeyword,String> {

}
