package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteIntake;
import com.seeka.app.bean.InstituteService;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;

public interface IInstituteDAO {

	void save(Institute obj);

	void update(Institute obj);

	Institute get(BigInteger id);

	List<BigInteger> getTopInstituteByCountry(BigInteger countryId/* , Long startIndex, Long pageSize */);

	List<Institute> getAll();

	List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey);

	List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj, String sortByField, String sortByType, String searchKeyword,
			Integer startIndex, BigInteger cityId, BigInteger instituteTypeId, Boolean isActive, Date updatedOn, Integer fromWorldRanking,
			Integer toWorldRanking, String campusType);

	InstituteResponseDto getInstituteByID(BigInteger instituteId);

	List<InstituteResponseDto> getInstitudeByCityId(BigInteger cityId);

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

	InstituteCategoryType getInstituteCategoryType(BigInteger instituteCategoryTypeId);

	List<Institute> getSecondayCampus(BigInteger countryId, BigInteger cityId, String name);

	void saveInstituteserviceDetails(InstituteService instituteServiceDetails);

	void deleteInstituteService(BigInteger id);

	void saveInstituteIntake(InstituteIntake instituteIntake);

	void deleteInstituteIntakeById(BigInteger id);

	List<String> getIntakesById(@Valid BigInteger id);

	List<InstituteCategoryType> getAllCategories();

	List<Institute> ratingWiseInstituteListByCountry(Country country);

	List<Institute> getAllInstituteByID(Collection<BigInteger> instituteId);

	List<BigInteger> getInstituteIdsBasedOnGlobalRanking(Long startIndex, Long pageSize);

	List<BigInteger> getInstitudeByCountry(List<BigInteger> distinctCountryIds);

	List<BigInteger> getRandomInstituteByCountry(List<BigInteger> countryIdList);

	int getCountOfInstitute(CourseSearchDto courseSearchDto, String searchKeyword, BigInteger cityId, BigInteger instituteTypeId, Boolean isActive,
			Date updatedOn, Integer fromWorldRanking, Integer toWorldRanking, String campusType);
}
