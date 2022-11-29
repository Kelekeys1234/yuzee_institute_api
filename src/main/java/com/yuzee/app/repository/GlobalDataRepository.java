package com.yuzee.app.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.GlobalData;

@Repository
public interface GlobalDataRepository extends MongoRepository<GlobalData, String> {

}
