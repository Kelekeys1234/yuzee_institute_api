package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.Intake;
import com.seeka.app.dto.InstituteTypeDto;

public interface IInstituteTypeService {

    public void save(InstituteTypeDto obj);

    public void update(InstituteTypeDto obj);

    public InstituteType get(String id);

    public List<Intake> getAllIntake();

    public List<InstituteTypeDto> getAllInstituteType();

    public List<InstituteTypeDto> getInstituteTypeByCountryName(String countryName);
}
