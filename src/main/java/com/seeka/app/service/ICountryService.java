package com.seeka.app.service;

import java.util.List;
import java.util.Map;

import com.seeka.app.dto.NearestCourseResponseDto;
import com.seeka.app.dto.NearestInstituteDTO;
import com.seeka.app.exception.NotFoundException;

public interface ICountryService {
	
    Map<String, Object> getCourseCountry();
    
    public List<NearestCourseResponseDto> getCourseByCountryName(String countryName, Integer pageNumber, Integer pageSize) throws NotFoundException;
    
    public List<NearestInstituteDTO> getInstituteByCountryName(String countryName, Integer pageNumber, Integer pageSize) throws NotFoundException;
}
