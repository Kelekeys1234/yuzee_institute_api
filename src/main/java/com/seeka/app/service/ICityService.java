package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.seeka.app.bean.City;
import com.seeka.app.dto.CityDto;

public interface ICityService {
    public List<City> getAll();

    public City get(BigInteger id);

    public List<City> getAllCitiesByCountry(BigInteger countryId);

    public void save(City obj);

    public List<City> getAllMultipleCitiesByCountry(String countryId);

    public Map<String, Object> save(CityDto city);
}
