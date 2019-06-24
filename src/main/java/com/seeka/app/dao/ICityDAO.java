package com.seeka.app.dao;

import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.City;

public interface ICityDAO {
    public List<City> getAll();

    public City get(BigInteger id);

    public List<City> getAllCitiesByCountry(BigInteger countryId);

    public void save(City obj);

    public List<City> getAllMultipleCitiesByCountry(String BigIntegers);
}
