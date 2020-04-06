package com.seeka.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.seeka.app.dto.CountryDto;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class CountryService implements ICountryService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public List<CountryDto> getAllCountries() {
		ResponseEntity<Map> result = restTemplate.getForEntity(IConstant.COMMON_CONNECTION_URL + "/country/getAll", Map.class);
		Map<String, Object> responseMap = result.getBody();
		System.out.println("responseMap------>"+responseMap);
		Map<String, Object> bodyResponseMap = (Map<String, Object>) responseMap.get("body");
		System.out.println("bodyResponseMap------>"+bodyResponseMap);
		List<CountryDto> countryDto = (List<CountryDto>) bodyResponseMap.get("data");
		System.out.println("dataResponseMap------>"+countryDto);
		return countryDto;
	}


}
