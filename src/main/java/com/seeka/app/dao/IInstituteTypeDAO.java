package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.Intake;

public interface IInstituteTypeDAO {

    void save(InstituteType obj);

    void update(InstituteType obj);

    InstituteType get(String id);

    List<Intake> getAllIntake();

    List<InstituteType> getAll();
    
    List<InstituteType> getByCountryName(String countryName);
}
