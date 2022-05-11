package com.yuzee.app.repository;

import com.yuzee.app.bean.InstituteCampus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteCampusRepository extends MongoRepository<InstituteCampus, String> {
}
