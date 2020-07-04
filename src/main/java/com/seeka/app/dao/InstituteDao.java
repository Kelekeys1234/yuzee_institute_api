package com.seeka.app.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteIntake;
import com.seeka.app.bean.InstituteService;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteGetRequestDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.dto.ServiceDto;

public interface InstituteDao {

	public void save(Institute obj);

	public void update(Institute obj);

	public Institute get(String id);

	public List<String> getTopInstituteByCountry(String countryId/* , Long startIndex, Long pageSize */);

	public List<Institute> getAll();

	public List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey);

	public List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj, String sortByField, String sortByType, String searchKeyword,
			Integer startIndex, String cityId, String instituteTypeId, Boolean isActive, Date updatedOn, Integer fromWorldRanking,
			Integer toWorldRanking);

	public InstituteResponseDto getInstituteByID(String instituteId);

	public List<InstituteResponseDto> getInstitudeByCityId(String cityId);

	public List<InstituteResponseDto> getInstituteByListOfCityId(String citisId);

	public List<Institute> searchInstitute(String sqlQuery);

	public int findTotalCount();

	public List<InstituteGetRequestDto> getAll(Integer pageNumber, Integer pageSize);

	public List<ServiceDto> getAllServices();

	public void delete(Institute obj);

	public List<Institute> instituteFilter(int startIndex, Integer maxSizePerPage, InstituteFilterDto instituteFilterDto);

	public int findTotalCountFilterInstitute(InstituteFilterDto instituteFilterDto);

	public List<InstituteGetRequestDto> autoSearch(int startIndex, Integer pageSize, String searchKey);

	public int findTotalCountForInstituteAutosearch(String searchKey);

	public InstituteCategoryType getInstituteCategoryType(String instituteCategoryTypeId);

	public List<Institute> getSecondayCampus(String countryId, String cityId, String name);

	public void saveInstituteserviceDetails(InstituteService instituteServiceDetails);

	public void deleteInstituteService(String id);

	public void saveInstituteIntake(InstituteIntake instituteIntake);

	public void deleteInstituteIntakeById(String id);

	public List<String> getIntakesById(@Valid String id);

	public List<InstituteCategoryType> getAllCategories();

	public List<Institute> ratingWiseInstituteListByCountry(String countryName);

	public List<Institute> getAllInstituteByID(Collection<String> instituteId);

	public List<String> getInstituteIdsBasedOnGlobalRanking(Long startIndex, Long pageSize);

	public List<String> getInstitudeByCountry(List<String> distinctCountryIds);

	public List<String> getRandomInstituteByCountry(List<String > countryIdList);

	public int getCountOfInstitute(CourseSearchDto courseSearchDto, String searchKeyword, String cityId, String instituteTypeId, Boolean isActive,
			Date updatedOn, Integer fromWorldRanking, Integer toWorldRanking);

	public Map<String, Integer> getDomesticRanking(List<String> instituteIdList);

	public List<InstituteResponseDto> getNearestInstituteListForAdvanceSearch(AdvanceSearchDto courseSearchDto);

	public List<String> getUserSearchInstituteRecommendation(Integer startIndex, Integer pageSize, String searchKeyword);
	
	public List<InstituteResponseDto> getDistinctInstituteListByName(Integer startIndex, Integer pageSize, String institueName);
	
	public int getDistinctInstituteCountByName(String skillName);
	
	public Optional<Institute> getInstituteByInstituteId (String instituteId);
	
	public List<InstituteResponseDto> getNearestInstituteList(Integer pageNumber, Integer pageSize, Double latitutde, Double longitude, Integer initialRadius);
	
	public Integer getTotalCountOfNearestInstitutes(Double latitude, Double longitude, Integer initialRadius);
	
	public Integer getCourseCount(final String id);
}
