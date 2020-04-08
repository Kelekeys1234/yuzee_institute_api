package com.seeka.app.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteIntake;
import com.seeka.app.bean.InstituteService;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.dto.NearestInstituteDTO;

public interface IInstituteDAO {

	void save(Institute obj);

	void update(Institute obj);

	Institute get(String id);

	List<String> getTopInstituteByCountry(String countryId/* , Long startIndex, Long pageSize */);

	List<Institute> getAll();

	List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey);

	List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj, String sortByField, String sortByType, String searchKeyword,
			Integer startIndex, String cityId, String instituteTypeId, Boolean isActive, Date updatedOn, Integer fromWorldRanking,
			Integer toWorldRanking, String campusType);

	InstituteResponseDto getInstituteByID(String instituteId);

	List<InstituteResponseDto> getInstitudeByCityId(String cityId);

	List<InstituteResponseDto> getInstituteByListOfCityId(String citisId);

	List<Institute> searchInstitute(String sqlQuery);

	int findTotalCount();

	List<Institute> getAll(Integer pageNumber, Integer pageSize);

	List<com.seeka.app.bean.Service> getAllServices();

	void delete(Institute obj);

	List<Institute> instituteFilter(int startIndex, Integer maxSizePerPage, InstituteFilterDto instituteFilterDto);

	int findTotalCountFilterInstitute(InstituteFilterDto instituteFilterDto);

	List<Institute> getInstituteCampusWithInstitue();

	List<Institute> autoSearch(int startIndex, Integer pageSize, String searchKey);

	int findTotalCountForInstituteAutosearch(String searchKey);

	InstituteCategoryType getInstituteCategoryType(String instituteCategoryTypeId);

	List<Institute> getSecondayCampus(String countryId, String cityId, String name);

	void saveInstituteserviceDetails(InstituteService instituteServiceDetails);

	void deleteInstituteService(String id);

	void saveInstituteIntake(InstituteIntake instituteIntake);

	void deleteInstituteIntakeById(String id);

	List<String> getIntakesById(@Valid String id);

	List<InstituteCategoryType> getAllCategories();

	List<Institute> ratingWiseInstituteListByCountry(String countryName);

	List<Institute> getAllInstituteByID(Collection<String> instituteId);

	List<String> getInstituteIdsBasedOnGlobalRanking(Long startIndex, Long pageSize);

	List<String> getInstitudeByCountry(List<String> distinctCountryIds);

	List<String> getRandomInstituteByCountry(List<String > countryIdList);

	int getCountOfInstitute(CourseSearchDto courseSearchDto, String searchKeyword, String cityId, String instituteTypeId, Boolean isActive,
			Date updatedOn, Integer fromWorldRanking, Integer toWorldRanking, String campusType);

	Map<String, Integer> getDomesticRanking(List<String> instituteIdList);

	List<NearestInstituteDTO> getNearestInstituteList(Integer pageNumber, Integer pageSize, Double latitude, Double longitude);

	List<String> getUserSearchInstituteRecommendation(Integer startIndex, Integer pageSize, String searchKeyword);
	
	List<InstituteResponseDto> getDistinctInstituteListByName(Integer startIndex, Integer pageSize, String institueName);
	
	int getDistinctInstituteCountByName(String skillName);
}
