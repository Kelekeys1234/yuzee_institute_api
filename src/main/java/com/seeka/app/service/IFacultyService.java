package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.Faculty;

public interface IFacultyService {

    public void save(Faculty obj);

    public void update(Faculty obj);

    public Faculty get(UUID id);

    public List<Faculty> getAll();

    public List<Faculty> getFacultyByCountryIdAndLevelId(UUID countryID, UUID levelId);

    public List<Faculty> getAllFacultyByCountryIdAndLevel();

    public List<Faculty> getFacultyByInstituteId(UUID instituteId);

    public List<Faculty> getFacultyByListOfInstituteId(String instituteId);
}
