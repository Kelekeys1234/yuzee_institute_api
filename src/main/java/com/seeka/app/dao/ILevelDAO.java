package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Level;

public interface ILevelDAO {

    public void save(Level obj);

    public void update(Level obj);

    public Level get(String id);

    public List<Level> getAll();

    public List<Level> getCourseTypeByCountryId(String countryID);

    public List<Level> getLevelByCountryId(String countryId);

    public List<Level> getAllLevelByCountry();

    public List<Level> getCountryLevel(String countryId);

	List<String> getAllLevelNamesByInstituteId(String instituteId);
}
