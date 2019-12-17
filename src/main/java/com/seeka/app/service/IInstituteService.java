package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteDomesticRankingHistory;
import com.seeka.app.bean.InstituteWorldRankingHistory;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteRequestDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.dto.NearestInstituteDTO;
import com.seeka.app.exception.ValidationException;

public interface IInstituteService {

	void save(Institute obj);

	void update(Institute obj);

	Institute get(BigInteger id);

	List<BigInteger> getTopInstituteIdByCountry(BigInteger countryId/* , Long startIndex, Long pageSize */);

	List<BigInteger> getRandomInstituteIdByCountry(List<BigInteger> countryId);

	List<Institute> getAll();

	List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey);

	List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj, String sortByField, String sortByType, String searchKeyword,
			Integer startIndex, BigInteger cityId, BigInteger instituteTypeId, Boolean isActive, Date updatedOn, Integer fromWorldRanking,
			Integer toWorldRanking, String campusType);

	InstituteResponseDto getInstituteByID(BigInteger instituteId);

	List<InstituteResponseDto> getInstitudeByCityId(BigInteger cityId);

	List<InstituteResponseDto> getInstituteByListOfCityId(String cityId);

	Map<String, Object> save(List<InstituteRequestDto> institute);

	Map<String, Object> getAllInstitute(Integer pageNumber, Integer pageSize);

	List<InstituteRequestDto> getById(BigInteger id) throws ValidationException;

	Map<String, Object> searchInstitute(@Valid String searchText);

	Map<String, Object> update(List<InstituteRequestDto> institute, @Valid BigInteger id);

	Map<String, Object> instituteFilter(InstituteFilterDto instituteFilterDto);

	Map<String, Object> autoSearch(Integer pageNumber, Integer pageSize, String searchKey);

	List<Institute> getAllInstitute();

	List<InstituteCategoryType> getAllCategories();

	void deleteInstitute(BigInteger id) throws ValidationException;

	List<Institute> ratingWiseInstituteListByCountry(Country country);

	List<InstituteResponseDto> getAllInstituteByID(final Collection<BigInteger> listInstituteId) throws ValidationException;

	List<BigInteger> getInstituteIdsBasedOnGlobalRanking(Long startIndex, Long pageSize);

	List<BigInteger> getInstituteIdsFromCountry(List<BigInteger> distinctCountryIds);

	int getCountOfInstitute(CourseSearchDto courseSearchDto, String searchKeyword, BigInteger cityId, BigInteger instituteTypeId, Boolean isActive,
			Date updatedOn, Integer fromWorldRanking, Integer toWorldRanking, String campusType);

	Integer getTotalCourseCountForInstitute(BigInteger instituteId);

	InstituteDomesticRankingHistory getHistoryOfDomesticRanking(BigInteger instituteId);

	InstituteWorldRankingHistory getHistoryOfWorldRanking(BigInteger instituteId);

	Map<BigInteger, Integer> getDomesticRanking(List<BigInteger> instituteIdList);

	List<NearestInstituteDTO> getNearestInstituteList(Integer pageNumber, Integer pageSize, Double latitude, Double longitude) throws ValidationException;

}
