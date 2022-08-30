package com.yuzee.app.repository;

import com.yuzee.app.bean.InstituteEnglishRequirements;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InstituteEnglishRequirementRepository extends MongoRepository<InstituteEnglishRequirements, String> {
    
    @Query(value = "{'id' : ?0}")
    InstituteEnglishRequirements find(String englishRequirementsId);
    
  
}
