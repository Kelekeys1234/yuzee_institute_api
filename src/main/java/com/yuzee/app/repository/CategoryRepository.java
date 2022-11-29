package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yuzee.app.bean.Category;

public interface CategoryRepository extends MongoRepository<Category,String>{

}
