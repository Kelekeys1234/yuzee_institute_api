package com.yuzee.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteCampus;

@Repository
public interface InstituteCampusRepository extends MongoRepository<InstituteCampus, String> {
}
