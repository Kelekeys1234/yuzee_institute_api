package com.seeka.app.service;

import java.util.Map;

import com.seeka.app.dto.NearestCoursesDto;
import com.seeka.app.dto.NearestInstituteDTO;
import com.seeka.app.exception.NotFoundException;

public interface ICountryService {
	
    Map<String, Object> getCourseCountry();
    
    public NearestCoursesDto getCourseByCountryName(String countryName, Integer pageNumber, Integer pageSize) throws NotFoundException;
    
    public NearestInstituteDTO getInstituteByCountryName(String countryName, Integer pageNumber, Integer pageSize) throws NotFoundException;
}
