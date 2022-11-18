package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Help;

@Repository
public interface HelpRepository extends MongoRepository<Help,String> {
}
