package com.seeka.app.service;

import java.util.List;
import java.util.Map;

import com.seeka.app.bean.InstituteType;
import com.seeka.app.dto.InstituteTypeDto;

public interface IInstituteTypeService {

    void save(InstituteTypeDto obj);

    void update(InstituteTypeDto obj);

    InstituteType get(String id);

    Map<String, Object> getAllIntake();

    List<InstituteType> getAllInstituteType();

    List<InstituteTypeDto> getInstituteTypeByCountryName(String countryName);
}
