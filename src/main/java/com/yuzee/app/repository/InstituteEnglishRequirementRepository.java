package com.yuzee.app.repository;

import com.yuzee.app.bean.InstituteEnglishRequirements;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstituteEnglishRequirementRepository extends MongoRepository<InstituteEnglishRequirements, UUID> {
    
    @Query(value = "{'id' : ?0}")
    InstituteEnglishRequirements find(UUID englishRequirementsId);
}
