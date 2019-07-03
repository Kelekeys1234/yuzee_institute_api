package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.Institute;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;

public interface IInstituteService {
	
	void save(Institute obj);
	void update(Institute obj);
	Institute get(BigInteger id);
	List<Institute> getAllInstituteByCountry(BigInteger countryId);
	List<Institute> getAll();
	List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey);
	List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj);
	InstituteResponseDto getInstituteByID(BigInteger instituteId);
    List<InstituteResponseDto> getInstitudeByCityId(BigInteger cityId);
    List<InstituteResponseDto> getInstituteByListOfCityId(String cityId);
}
