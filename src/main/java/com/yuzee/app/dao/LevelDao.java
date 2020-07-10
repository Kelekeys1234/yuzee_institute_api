package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.Level;
import com.yuzee.app.dto.LevelDto;

public interface LevelDao {

    public void save(Level obj);

    public void update(Level obj);

    public Level get(String id);

    public List<Level> getAll();

    public List<Level> getCourseTypeByCountryId(String countryID);

    public List<Level> getLevelByCountryId(String countryId);

    public List<Level> getAllLevelByCountry();

    public List<LevelDto> getCountryLevel(String countryId);
    
	public List<String> getAllLevelNamesByInstituteId(final String instituteId);
}
