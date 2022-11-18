package com.yuzee.app.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteCampus;
import com.yuzee.app.bean.InstituteCategoryType;
import com.yuzee.app.bean.InstituteDomesticRankingHistory;
import com.yuzee.app.bean.InstituteFacility;
import com.yuzee.app.bean.InstituteService;
import com.yuzee.app.bean.InstituteType;
import com.yuzee.app.bean.Service;
import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.InstituteFacultyDto;
import com.yuzee.app.dto.InstituteFilterDto;
import com.yuzee.app.dto.InstituteGetRequestDto;
import com.yuzee.app.dto.InstituteResponseDto;
import com.yuzee.app.dto.InstituteServiceDto;
import com.yuzee.app.dto.InstituteTypeDto;
import com.yuzee.app.dto.ServiceDto;
import com.yuzee.common.lib.exception.NotFoundException;

public interface InstituteDao {

	public Institute addUpdateInstitute(Institute obj);

	public Institute get(String id);

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

	public InstituteCategoryType getInstituteCategoryType(String instituteId, String instituteCategoryTypeId);

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

	public List<Institute> findByIds(List<UUID> instituteIds);

	public List<Institute> findByReadableIdIn(List<String> readableIds);

	public Institute findByReadableId(String readableId);

	@Cacheable(value = "cacheInstituteMap", unless = "#result == null")
	public Map<String, String> getAllInstituteMap();

	@Cacheable(value = "cacheInstitute", key = "#instituteId", unless = "#result == null", condition = "#instituteId!=null")
	public Optional<Institute> getInstitute(String instituteId);

	List<Institute> getByInstituteName(String instituteName);

	List<InstituteFacility> getInstituteFaculties(String instituteId);

	Optional<Institute> getInstituteEnglishRequirementsById(String instituteEnglishRequirementsId);

	void deleteInstituteEnglishRequirementsById(String instituteEnglishRequirementsId);

	List<InstituteType> getByCountryName(String countryName);

	List<String> findIntakesById(String id);

	List<InstituteTypeDto> getAllInstituteType();

	List<Institute> getBySearchText(String searchText);

	List<InstituteFacility> getAllInstituteFacility(String instituteId);

	void deleteFacilityByIdAndInstituteId(String instituteFacilityId, String instituteId);

	public List<InstituteServiceDto> saveAll(Institute institute);

	void saveAllInstitutes(List<Institute> institutesFromDb);

	List<Service> getAllServiceByIds(List<String> serviceIds);

	Page<Service> getAllServices(Pageable pageable);

	Service getServiceById(String facilityId);

	Optional<Service> findById(String facilityId);

	List<InstituteDomesticRankingHistory> getInstituteDomesticRankingHistories(String instituteId);

	List<InstituteCampus> findInstituteCampuses(String instituteId);

	List<Service> findServiceByName(List<String> allNames);

	List<ServiceDto> addUpdateServices(List<Service> services);

	InstituteType getInstituteTypeByNameAndCountry(String instituteType, String countryName);

	Institute getInstituteByInstituteEnglishRequirementId(UUID fromString);
}
