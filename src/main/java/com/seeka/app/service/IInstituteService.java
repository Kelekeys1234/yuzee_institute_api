package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteRequestDto;
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

	Map<String, Object> save(@Valid List<InstituteRequestDto> institute);

	Map<String, Object> getAllInstitute(Integer pageNumber, Integer pageSize);

	Map<String, Object> getById(@Valid BigInteger id);

	Map<String, Object> searchInstitute(@Valid String searchText);

	Map<String, Object> update(List<InstituteRequestDto> institute, @Valid BigInteger id);

	Map<String, Object> instituteFilter(InstituteFilterDto instituteFilterDto);

	Map<String, Object> autoSearch(Integer pageNumber, Integer pageSize, String searchKey);

	List<Institute> getAllInstitute();
	
	List<InstituteCategoryType> getAllCategories();
}
