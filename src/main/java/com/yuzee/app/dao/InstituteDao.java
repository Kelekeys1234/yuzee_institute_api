package com.yuzee.app.dao;

import java.util.*;

import javax.validation.Valid;

import com.yuzee.app.bean.*;
import com.yuzee.app.dto.*;
import org.springframework.cache.annotation.Cacheable;

import com.yuzee.common.lib.exception.NotFoundException;
import org.springframework.data.domain.Sort;

public interface InstituteDao {

	public Institute addUpdateInstitute(Institute obj);

	public Institute get(UUID id);

	public List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj, String sortByField,
			String sortByType, String searchKeyword, Integer startIndex, String cityId, String instituteTypeId,
			Boolean isActive, Date updatedOn, Integer fromWorldRanking, Integer toWorldRanking);

	public InstituteResponseDto getInstituteById(String instituteId);

	public List<Institute> searchInstitute(String sqlQuery);

	public int findTotalCount();

	public List<InstituteGetRequestDto> getAll(Integer pageNumber, Integer pageSize);

	public List<Institute> instituteFilter(int startIndex, Integer maxSizePerPage,
			InstituteFilterDto instituteFilterDto);

	public int findTotalCountFilterInstitute(InstituteFilterDto instituteFilterDto);

	public List<InstituteGetRequestDto> autoSearch(int startIndex, Integer pageSize, String searchKey);

	public int findTotalCountForInstituteAutoSearch(String searchKey);

	public InstituteCategoryType getInstituteCategoryType(String instituteCategoryTypeId);

	public List<Institute> getSecondaryCampus(String countryId, String cityId, String name);

	public void saveInstituteServiceDetails(InstituteService instituteServiceDetails);

	public void deleteInstituteService(String id);

	public void saveInstituteIntake(String instituteIntake);

	public void deleteInstituteIntakeById(String id);

	public List<String> getIntakesById(@Valid String id);

	public List<InstituteCategoryType> getAllCategories();

//	public List<InstituteCategoryType> addInstituteCategoryTypes(List<InstituteCategoryType> instituteCategoryTypes);

	public List<Institute> ratingWiseInstituteListByCountry(String countryName);

	public List<String> getInstituteIdsBasedOnGlobalRanking(Long startIndex, Long pageSize);

	public List<String> getRandomInstituteByCountry(List<String> countryIdList);

	public int getCountOfInstitute(CourseSearchDto courseSearchDto, String searchKeyword, String cityId,
			String instituteTypeId, Boolean isActive, Date updatedOn, Integer fromWorldRanking, Integer toWorldRanking);

	public Map<String, Integer> getDomesticRanking(List<String> instituteIdList);

	public List<InstituteResponseDto> getNearestInstituteListForAdvanceSearch(AdvanceSearchDto courseSearchDto);

	public List<String> getUserSearchInstituteRecommendation(Integer startIndex, Integer pageSize,
			String searchKeyword);

	public List<InstituteResponseDto> getInstitutesByInstituteName(Integer startIndex, Integer pageSize,
			String instituteName);

	public int getDistinctInstituteCountByName(String skillName);

	public Optional<Institute> getInstituteByInstituteId(UUID instituteId);

	public List<InstituteResponseDto> getNearestInstituteList(Integer pageNumber, Integer pageSize, Double latitutde,
			Double longitude, Integer initialRadius);

	public Integer getTotalCountOfNearestInstitutes(Double latitude, Double longitude, Integer initialRadius);

	public List<Institute> getInstituteCampuses(String instituteId, String instituteName) throws NotFoundException;

	public List<InstituteFacultyDto> getInstituteFaculties(String instituteId, Sort sort) throws NotFoundException;

	public List<InstituteResponseDto> findByIds(List<String> instituteIds);

	public List<Institute> findAllById(List<String> instituteIds);

	public List<Institute> findByReadableIdIn(List<String> readableIds);

	public Institute findByReadableId(String readableId);
	
	@Cacheable(value = "cacheInstituteMap", unless = "#result == null")
	public Map<String, String> getAllInstituteMap();

	@Cacheable(value = "cacheInstitute", key = "#instituteId", unless = "#result == null", condition="#instituteId!=null")
	public Institute getInstitute(String instituteId);

	public List<Institute> saveAll(List<Institute> institutesFromDb);

	List<Institute> getByInstituteName(String instituteName);

	List<InstituteFacility> getInstituteFaculties(String instituteId);

	Optional<Institute> getInstituteEnglishRequirementsById(String instituteEnglishRequirementsId);

	void deleteInstituteEnglishRequirementsById(String instituteEnglishRequirementsId);

	List<InstituteType> getByCountryName(String countryName);

	List<String> findIntakesById(String id);

	List<InstituteTypeDto> getAllInstituteType();

	List<Institute> getBySearchText(String searchText);
}
