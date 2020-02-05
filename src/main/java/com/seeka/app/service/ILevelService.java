package com.seeka.app.service;

import java.math.BigInteger;

import java.util.List;
import java.util.Map;

import com.seeka.app.bean.Level;

public interface ILevelService {

    public void save(Level obj);

    public void update(Level obj);

    public Level get(String id);

    public List<Level> getAll();

    public List<Level> getCourseTypeByCountryId(BigInteger countryID);

    public List<Level> getLevelByCountryId(String countryId);

    public List<Level> getAllLevelByCountry();

    public Map<String, Object> getCountryLevel(String countryId);
}
