package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.HelpAnswer;

@Repository
public interface HelpAnswerRepository extends MongoRepository<HelpAnswer,String>{
}
