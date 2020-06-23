package com.seeka.app.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteDomesticRankingHistory;
import com.seeka.app.bean.InstituteWorldRankingHistory;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseSearchDto;
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

	void save(Institute obj);

	void update(Institute obj);

	Institute get(String id);

	List<String> getTopInstituteIdByCountry(String countryId/* , Long startIndex, Long pageSize */);

	List<String> getRandomInstituteIdByCountry(List<String> countryId);

	List<Institute> getAll();

	List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey);

	List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj, String sortByField, String sortByType, String searchKeyword,
			Integer startIndex, String cityId, String instituteTypeId, Boolean isActive, Date updatedOn, Integer fromWorldRanking,
			Integer toWorldRanking);

	InstituteResponseDto getInstituteByID(String instituteId);

	List<InstituteResponseDto> getInstitudeByCityId(String cityId);

	List<InstituteResponseDto> getInstituteByListOfCityId(String cityId);

	public void saveInstitute(List<InstituteRequestDto> institute);

	PaginationResponseDto getAllInstitute(Integer pageNumber, Integer pageSize);

	List<InstituteRequestDto> getById(String id) throws ValidationException;

	public List<InstituteGetRequestDto> searchInstitute(@Valid String searchText);

	public void updateInstitute(List<InstituteRequestDto> institute, @Valid String id);

	public PaginationResponseDto instituteFilter(InstituteFilterDto instituteFilterDto);

	public PaginationResponseDto autoSearch(Integer pageNumber, Integer pageSize, String searchKey);

	List<Institute> getAllInstitute();

	List<InstituteCategoryType> getAllCategories();

	void deleteInstitute(String id) throws ValidationException;

	List<Institute> ratingWiseInstituteListByCountry(String countryName);

	List<InstituteResponseDto> getAllInstituteByID(final Collection<String> listInstituteId) throws ValidationException;

	List<String> getInstituteIdsBasedOnGlobalRanking(Long startIndex, Long pageSize);

	List<String> getInstituteIdsFromCountry(List<String> distinctCountryIds);

	int getCountOfInstitute(CourseSearchDto courseSearchDto, String searchKeyword, String cityId, String instituteTypeId, Boolean isActive,
			Date updatedOn, Integer fromWorldRanking, Integer toWorldRanking);

	Integer getTotalCourseCountForInstitute(String instituteId);

	InstituteDomesticRankingHistory getHistoryOfDomesticRanking(String instituteId);

	InstituteWorldRankingHistory getHistoryOfWorldRanking(String instituteId);

	Map<String, Integer> getDomesticRanking(List<String> instituteIdList);

	public NearestInstituteDTO getNearestInstituteList(AdvanceSearchDto courseSearchDto) throws Exception;
	
	public List<InstituteResponseDto> getDistinctInstituteList(Integer startIndex, Integer pageSize, String instituteName);
	
	public int getDistinctInstituteCount(String instituteName);
	
	public NearestInstituteDTO getInstitutesUnderBoundRegion(Integer pageNumber, Integer pageSize, List<LatLongDto> latLongDtos) throws ValidationException;

}
