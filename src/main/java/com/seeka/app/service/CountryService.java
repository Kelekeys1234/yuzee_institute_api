package com.seeka.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dto.CountryDto;

@Service
@Transactional
public class CountryService implements ICountryService {

	@Autowired
	private ICourseDAO courseDAO;

	@Override
	public Map<String, Object> getCourseCountry() {
		Map<String, Object> response = new HashMap<>();
		List<CountryDto> countries = new ArrayList<CountryDto>();
		try {
			countries = courseDAO.getCourseCountry();
			if (countries != null && !countries.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", "Country fetched successfully");
				response.put("courses", countries);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", "Country not found");
			}
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}
}
