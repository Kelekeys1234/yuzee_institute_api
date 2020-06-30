package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.Intake;
import com.seeka.app.dto.InstituteTypeDto;

public interface InstituteTypeDao {

    public void save(InstituteType obj);

    public void update(InstituteType obj);

    public InstituteType get(String id);

    public List<Intake> getAllIntake();

    public List<InstituteTypeDto> getAll();
    
    public List<InstituteType> getByCountryName(String countryName);
}
