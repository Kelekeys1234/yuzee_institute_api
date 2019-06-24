package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.City;
import com.seeka.app.bean.CityImages;
import com.seeka.app.bean.Country;
import com.seeka.app.dao.CountryImagesDAO;
import com.seeka.app.dao.ICityDAO;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dto.CityDto;
import com.seeka.app.dto.CityImageDto;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class CityService implements ICityService {

    @Autowired
    private ICityDAO dao;

    @Autowired
    private ICountryDAO countryDAO;

    @Autowired
    private CountryImagesDAO countryImagesDAO;

    @Override
    public void save(City obj) {
        dao.save(obj);

    }

    @Override
    public List<City> getAll() {
        return dao.getAll();
    }

    @Override
    public City get(BigInteger id) {
        return dao.get(id);
    }

    @Override
    public List<City> getAllCitiesByCountry(BigInteger countryId) {
        return dao.getAllCitiesByCountry(countryId);
    }

    @Override
    public List<City> getAllMultipleCitiesByCountry(String countryId) {
        String[] citiesArray = countryId.split(",");
        String tempList = "";
        List<BigInteger> list = new ArrayList<>();
        for (String id : citiesArray) {
            list.add(new BigInteger(id));
            tempList = tempList + "," + "'" + new BigInteger(id) + "'";
        }
        return dao.getAllMultipleCitiesByCountry(tempList.substring(1, tempList.length()));
    }

    @Override
    public Map<String, Object> save(CityDto cityObj) {
        Map<String, Object> response = new HashMap<String, Object>();
        boolean status = true;
        try {
            Country country = null;
            if (cityObj.getCountry() != null) {
                country = countryDAO.get(cityObj.getCountry().getId());
            }
            City city = CommonUtil.convertCityDTOToBean(cityObj, country);
            dao.save(city);
            if (cityObj != null && cityObj.getCityImageDtos() != null && !cityObj.getCityImageDtos().isEmpty()) {
                for (CityImageDto dto : cityObj.getCityImageDtos()) {
                    CityImages cityImages = new CityImages();
                    cityImages.setCity(city);
                    cityImages.setImageName(dto.getImageName());
                    cityImages.setImagePath(dto.getImagePath());
                    cityImages.setCity(city);
                    cityImages.setCreatedOn(new Date());
                    cityImages.setUpdatedOn(new Date());
                    cityImages.setIsActive(true);
                    countryImagesDAO.save(cityImages);
                }
            }
        } catch (Exception exception) {
            status = false;
            exception.printStackTrace();
        }
        if (status) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.CITY_SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", IConstant.FAIL);
        }
        return response;
    }
}
