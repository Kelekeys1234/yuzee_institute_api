package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.Institute;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;

public interface IInstituteService {
	
	public void save(Institute obj);
	public void update(Institute obj);
	public Institute get(BigInteger id);
	public List<Institute> getAllInstituteByCountry(BigInteger countryId);
	public List<Institute> getAll();
	public List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey);
	public List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj);
	public InstituteResponseDto getInstituteByID(BigInteger instituteId);
    public List<InstituteResponseDto> getInstitudeByCityId(BigInteger cityId);
    public List<InstituteResponseDto> getInstituteByListOfCityId(String cityId);
}
