package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.dao.ICityDAO;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dto.CityDto;
import com.seeka.app.dto.UpdateCityDto;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class CityService implements ICityService {

	@Autowired
	private ICityDAO iCityDAO;

	@Autowired
	private ICountryDAO iCountryDAO;

	@Override
	public void save(City obj) {
		iCityDAO.save(obj);

	}

	@Override
	public List<City> getAll() {
		return iCityDAO.getAll();
	}

	@Override
	public City get(String id) {
		return iCityDAO.get(id);
	}

	@Override
	public List<City> getAllCitiesByCountry(String countryId) {
		return iCityDAO.getAllCitiesByCountry(countryId);
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
		return iCityDAO.getAllMultipleCitiesByCountry(tempList.substring(1, tempList.length()));
	}

	@Override
	public Map<String, Object> save(CityDto cityObj) {
		Map<String, Object> response = new HashMap<String, Object>();
		boolean status = true;
		try {
			Country country = null;
			if (cityObj.getCountry() != null) {
				country = iCountryDAO.get(cityObj.getCountry().getId());
			}
			City city = CommonUtil.convertCityDTOToBean(cityObj, country);
			iCityDAO.save(city);
		} catch (Exception exception) {
			status = false;
			exception.printStackTrace();
		}
		if (status) {
			response.put("status", HttpStatus.OK.value());
			response.put("message", IConstant.CITY_SUCCESS_MESSAGE);
		} else {
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", IConstant.SQL_ERROR);
		}
		return response;
	}

	@Override
	public Map<String, Object> update(BigInteger id, UpdateCityDto city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getAllCityNames(Integer pageNumber, Integer pageSize, String searchString) {
		return iCityDAO.getAllCityNames(pageNumber, pageSize, searchString);
	}

	@Override
	public Integer getAllCityNamesCount(String searchString) {
		return iCityDAO.getAllCityNamesCount(searchString);
	}
}
