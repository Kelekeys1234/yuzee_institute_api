package com.yuzee.app.dao;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

import com.yuzee.app.bean.InstituteEnglishRequirements;
import com.yuzee.common.lib.exception.ValidationException;

public interface InstituteEnglishRequirementDao {
  
    public InstituteEnglishRequirements addUpdateInstituteEnglishRequirements(
            InstituteEnglishRequirements instituteEnglishRequirements) throws ValidationException;

    public InstituteEnglishRequirements getInstituteEnglishRequirementsById(UUID englishRequirementsId);
    public List<InstituteEnglishRequirements> getListInstituteEnglishRequirementsById(UUID englishRequirementsId);
    List<InstituteEnglishRequirements>getAll();

    public void deleteInstituteEnglishRequirementsById(UUID englishRequirementsId);
}
