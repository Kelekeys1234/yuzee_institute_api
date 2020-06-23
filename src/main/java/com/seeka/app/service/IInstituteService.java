package com.seeka.app.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteWorldRankingHistory;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteDomesticRankingHistoryDto;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteGetRequestDto;
import com.seeka.app.dto.InstituteRequestDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.dto.LatLongDto;
import com.seeka.app.dto.NearestInstituteDTO;
import com.seeka.app.dto.PaginationResponseDto;
import com.seeka.app.exception.ValidationException;

public interface IInstituteService {

	public void save(Institute obj);

	public void update(Institute obj);

	public Institute get(String id);

	public List<String> getTopInstituteIdByCountry(String countryId/* , Long startIndex, Long pageSize */);

	public List<String> getRandomInstituteIdByCountry(List<String> countryId);

	public List<Institute> getAll();

	public List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey);

	public List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj, String sortByField, String sortByType, 
			String searchKeyword, Integer startIndex, String cityId, String instituteTypeId, Boolean isActive, Date updatedOn, 
			Integer fromWorldRanking, Integer toWorldRanking);

	public InstituteResponseDto getInstituteByID(String instituteId);

	public List<InstituteResponseDto> getInstitudeByCityId(String cityId);

	public List<InstituteResponseDto> getInstituteByListOfCityId(String cityId);

	public void saveInstitute(List<InstituteRequestDto> institute);

	public PaginationResponseDto getAllInstitute(Integer pageNumber, Integer pageSize);

	public List<InstituteRequestDto> getById(String id) throws ValidationException;

	public List<InstituteGetRequestDto> searchInstitute(@Valid String searchText);

	public void updateInstitute(List<InstituteRequestDto> institute, @Valid String id);

	public PaginationResponseDto instituteFilter(InstituteFilterDto instituteFilterDto);

	public PaginationResponseDto autoSearch(Integer pageNumber, Integer pageSize, String searchKey);

	public List<Institute> getAllInstitute();

	public List<InstituteCategoryType> getAllCategories();

	public void deleteInstitute(String id) throws ValidationException;

	public List<Institute> ratingWiseInstituteListByCountry(String countryName);

	public List<InstituteResponseDto> getAllInstituteByID(final Collection<String> listInstituteId) throws ValidationException;

	public List<String> getInstituteIdsBasedOnGlobalRanking(Long startIndex, Long pageSize);

	public List<String> getInstituteIdsFromCountry(List<String> distinctCountryIds);

	public int getCountOfInstitute(CourseSearchDto courseSearchDto, String searchKeyword, String cityId, String instituteTypeId, Boolean isActive,
			Date updatedOn, Integer fromWorldRanking, Integer toWorldRanking);

	public Integer getTotalCourseCountForInstitute(String instituteId);

	public List<InstituteDomesticRankingHistoryDto> getHistoryOfDomesticRanking(String instituteId);

	public InstituteWorldRankingHistory getHistoryOfWorldRanking(String instituteId);

	public Map<String, Integer> getDomesticRanking(List<String> instituteIdList);

	public NearestInstituteDTO getNearestInstituteList(AdvanceSearchDto courseSearchDto) throws Exception;
	
	public List<InstituteResponseDto> getDistinctInstituteList(Integer startIndex, Integer pageSize, String instituteName);
	
	public int getDistinctInstituteCount(String instituteName);
	
	public NearestInstituteDTO getInstitutesUnderBoundRegion(Integer pageNumber, Integer pageSize, List<LatLongDto> latLongDtos) 
			throws ValidationException;

}
