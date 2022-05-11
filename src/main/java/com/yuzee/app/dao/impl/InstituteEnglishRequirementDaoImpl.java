package com.yuzee.app.dao.impl;

import com.yuzee.app.bean.InstituteEnglishRequirements;
import com.yuzee.app.dao.InstituteEnglishRequirementDao;
import com.yuzee.app.repository.InstituteEnglishRequirementRepository;
import com.yuzee.common.lib.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
public class InstituteEnglishRequirementDaoImpl implements InstituteEnglishRequirementDao {

    @Autowired
    private InstituteEnglishRequirementRepository instituteEnglishRequirementRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public InstituteEnglishRequirements addUpdateInstituteEnglishRequirements(InstituteEnglishRequirements instituteEnglishRequirements) throws ValidationException {
        return instituteEnglishRequirementRepository.save(instituteEnglishRequirements);
    }

    @Override
    public InstituteEnglishRequirements getInstituteEnglishRequirementsById(UUID englishRequirementsId) {
        Query mongoQuery = new Query();
        mongoQuery.addCriteria(Criteria.where("id").is(englishRequirementsId));
        return instituteEnglishRequirementRepository.find(englishRequirementsId);
    }

    @Override
    public void deleteInstituteEnglishRequirementsById(UUID englishRequirementsId) {
        instituteEnglishRequirementRepository.deleteById(englishRequirementsId);
    }
}
