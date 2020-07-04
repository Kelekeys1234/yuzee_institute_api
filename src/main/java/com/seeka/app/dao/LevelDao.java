package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Level;
import com.seeka.app.dto.LevelDto;

public interface LevelDao {

    public void save(Level obj);

    public void update(Level obj);

    public Level get(String id);

    public List<Level> getAll();

    public List<Level> getCourseTypeByCountryId(String countryID);

    public List<Level> getLevelByCountryId(String countryId);

    public List<Level> getAllLevelByCountry();

    public List<LevelDto> getCountryLevel(String countryId);

	public List<String> getAllLevelNamesByInstituteId(String instituteId);
}
