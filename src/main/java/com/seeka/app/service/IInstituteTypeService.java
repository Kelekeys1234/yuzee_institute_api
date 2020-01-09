package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.seeka.app.bean.InstituteType;

public interface IInstituteTypeService {

    void save(InstituteType obj);

    void update(InstituteType obj);

    InstituteType get(BigInteger id);

    Map<String, Object> getAllIntake();

    List<InstituteType> getAllInstituteType();
}
