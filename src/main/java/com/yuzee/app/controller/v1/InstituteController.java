package com.yuzee.app.controller.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.bean.InstituteCategoryType;
import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseScholarshipAndFacultyCountDto;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.InstituteCampusDto;
import com.yuzee.app.dto.InstituteDomesticRankingHistoryDto;
import com.yuzee.app.dto.InstituteFacultyDto;
import com.yuzee.app.dto.InstituteFilterDto;
import com.yuzee.app.dto.InstituteGetRequestDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.InstituteResponseDto;
import com.yuzee.app.dto.InstituteTimingResponseDto;
import com.yuzee.app.dto.InstituteTypeDto;
import com.yuzee.app.dto.InstituteWorldRankingHistoryDto;
import com.yuzee.app.dto.LatLongDto;
import com.yuzee.app.dto.NearestInstituteDTO;
import com.yuzee.app.dto.PaginationDto;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.PaginationUtilDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.endpoint.InstituteInterface;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.handler.StorageHandler;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.processor.InstituteTimingProcessor;
import com.yuzee.app.processor.InstituteTypeProcessor;
import com.yuzee.app.util.CommonUtil;
import com.yuzee.app.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("instituteControllerV1")
public class InstituteController implements InstituteInterface {

	@Autowired
	private InstituteProcessor instituteProcessor;

	@Autowired
	private InstituteTypeProcessor instituteTypeProcessor;

	@Autowired
	private StorageHandler storageHandler;
	
	@Autowired
	private InstituteTimingProcessor instituteTimingProcessor;

	@Override
	public ResponseEntity<?> saveInstituteType(final InstituteTypeDto instituteTypeDto) throws Exception {
		log.info("Start process to save new institute types in DB");
		instituteTypeProcessor.addUpdateInstituteType(instituteTypeDto);
		return new GenericResponseHandlers.Builder().setMessage("InstituteType Added successfully")
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getInstituteTypeByCountry(String countryName) throws Exception {
		log.info("Start process to fetch instituteType from DB for countryName = [}",countryName);
		List<InstituteTypeDto> listOfInstituteTypes = instituteTypeProcessor.getInstituteTypeByCountryName(countryName);
		return new GenericResponseHandlers.Builder().setData(listOfInstituteTypes).setMessage("Institute types fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> instituteSearch(final CourseSearchDto request) throws Exception {
		log.info("Start process to searching institute in DB");
		return getInstitutesBySearchFilters(request, null, null, null, null, null, null, null, null, null);
	}

	@Override
	public ResponseEntity<?> instituteSearch(final Integer pageNumber, final Integer pageSize, final List<String> countryNames, 
			final List<String> facultyIds,final List<String> levelIds, final String cityName, final String instituteType, final Boolean isActive,
			final Date updatedOn, final Integer fromWorldRanking, final Integer toWorldRanking, final String sortByField, final String sortByType, 
			final String searchKeyword, final Double latitutde, final Double longitude) throws ValidationException {
		log.info("Start process to searching institute based on passed filters");
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setCountryNames(countryNames);
		courseSearchDto.setFacultyIds(facultyIds);
		courseSearchDto.setLevelIds(levelIds);
		courseSearchDto.setPageNumber(pageNumber);
		courseSearchDto.setMaxSizePerPage(pageSize);
		courseSearchDto.setLatitude(latitutde);
		courseSearchDto.setLongitude(longitude);
		return getInstitutesBySearchFilters(courseSearchDto, sortByField, sortByType, searchKeyword, cityName, instituteType, isActive, updatedOn,
				fromWorldRanking, toWorldRanking);
	}

	private ResponseEntity<?> getInstitutesBySearchFilters(final CourseSearchDto request, final String sortByField, final String sortByType,
			final String searchKeyword, final String cityId, final String instituteTypeId, final Boolean isActive, final Date updatedOn,
			final Integer fromWorldRanking, final Integer toWorldRanking) throws ValidationException {
		log.debug("Inside getInstitutesBySearchFilters() method");
		int startIndex = PaginationUtil.getStartIndex(request.getPageNumber(), request.getMaxSizePerPage());
		log.info("Calling DAO layer to search institutes based on passed filters");
		List<InstituteResponseDto> instituteList = instituteProcessor.getAllInstitutesByFilter(request, sortByField, sortByType, searchKeyword, startIndex,
				cityId, instituteTypeId, isActive, updatedOn, fromWorldRanking, toWorldRanking);
		if(!CollectionUtils.isEmpty(instituteList)) {
			log.info("Institutes fetched from DB, now iterating data to call Storage service");
			instituteList.stream().forEach(instituteResponseDto -> {
				try {
					log.info("Calling Storage service to get imageCategories for Institute");
					List<StorageDto> storageDTOList = storageHandler.getStorages(instituteResponseDto.getId(), EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.IMAGES);
					instituteResponseDto.setStorageList(storageDTOList);
				} catch (NotFoundException | InvokeException e) {
					log.error("Error invoking Storage service having exception = {}",e);
				}
				log.info("fetching instituteTiming from DB for instituteId ={}", instituteResponseDto.getId());
				InstituteTimingResponseDto instituteTimingResponseDto = instituteTimingProcessor.getInstituteTimeByInstituteId(instituteResponseDto.getId());
				instituteResponseDto.setInstituteTiming(instituteTimingResponseDto);
				if(!ObjectUtils.isEmpty(request.getLatitude()) && !ObjectUtils.isEmpty(request.getLongitude()) && 
						!ObjectUtils.isEmpty(instituteResponseDto.getLatitude()) && !ObjectUtils.isEmpty(instituteResponseDto.getLongitude())) {
					log.info("Calculating distance between institutes lat and long and user lat and long");
					double distance = CommonUtil.getDistanceFromLatLonInKm(request.getLatitude(), request.getLongitude(), 
							instituteResponseDto.getLatitude(), instituteResponseDto.getLongitude());
					instituteResponseDto.setDistance(distance);
				}
			});
		}
		log.info("Fetching totalCount of institutes from DB based on passed filters to calculate pagination");
		int totalCount = instituteProcessor.getCountOfInstitute(request, searchKeyword, cityId, instituteTypeId, isActive, updatedOn, fromWorldRanking,
				toWorldRanking);
		log.info("Calling pagination class to calculate pagination based on startIndex, pageSixe and totalCount of institutes");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, request.getMaxSizePerPage(), totalCount);
		log.info("Adding values in paginationResponse DTO and returning final response");
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		paginationResponseDto.setResponse(instituteList);
		paginationResponseDto.setTotalCount(totalCount);
		paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		return new GenericResponseHandlers.Builder().setData(paginationResponseDto).setMessage("Institutes displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getAllRecommendedInstitutes(final CourseSearchDto request) throws Exception {
		log.debug("Inside getAllRecommendedInstitutes() method");
		List<InstituteResponseDto> instituteResponse = new ArrayList<>();
		Boolean showMore = false;
		Integer maxCount = 0, totalCount = 0;
		int startIndex = PaginationUtil.getStartIndex(request.getPageNumber(), request.getMaxSizePerPage());
		if (request.getPageNumber() > PaginationUtil.courseResultPageMaxSize) {
			log.error("Maximum course limit per is " + PaginationUtil.courseResultPageMaxSize);
			throw new ValidationException("Maximum course limit per is " + PaginationUtil.courseResultPageMaxSize);
		}
		if (null == request.getUserId()) {
			log.error("UserId is required");
			throw new ValidationException("UserId is required");
		}
		log.info("Calling DAO layer to get all institutes based on filters");
		List<InstituteResponseDto> instituteResponseDtoList = instituteProcessor.getAllInstitutesByFilter(request, null, null, null,
				startIndex, null, null, null, null, null, null);
		if(!CollectionUtils.isEmpty(instituteResponseDtoList)) {
			log.info("Filtered institutes coming from DB, hence start iterating");
			totalCount = instituteResponseDtoList.get(0).getTotalCount();
			maxCount = instituteResponseDtoList.size();
			instituteResponseDtoList.stream().forEach(instituteResponseDto -> {
				try {
					log.info("Invoking storage service to fetch images for institutes");
					List<StorageDto> storageDTOList = storageHandler.getStorages(instituteResponseDto.getId(), EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.IMAGES);
					instituteResponseDto.setStorageList(storageDTOList);
				} catch (NotFoundException | InvokeException e) {
					log.error("Error invoking Storage service having exception {}", e);
				}
				log.info("fetching instituteTiming from DB for instituteId = {}", instituteResponseDto.getId());
				InstituteTimingResponseDto instituteTimingResponseDto = instituteTimingProcessor.getInstituteTimeByInstituteId(instituteResponseDto.getId());
				instituteResponseDto.setInstituteTiming(instituteTimingResponseDto);
				instituteResponse.add(instituteResponseDto);
			});
		}
		if (request.getMaxSizePerPage() == maxCount) {
			log.info("if maxSize and max count are equal then showMore is visible");
			showMore = true;
		}
		log.info("Adding values in PaginationDTO class and return in final response");
		PaginationDto paginationDto = new PaginationDto(totalCount, showMore,instituteResponse);
		return new GenericResponseHandlers.Builder().setData(paginationDto).setMessage("Recommended Institutes displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstituteByCityName(final String cityName) throws Exception {
		log.info("Start process to fetch institutes from DB for cityName = {}", cityName);
		List<InstituteResponseDto> institutes = instituteProcessor.getInstituteByCityName(cityName);
		return new GenericResponseHandlers.Builder().setData(institutes).setMessage("Institutes displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> save(final List<InstituteRequestDto> institutes) throws Exception {
		log.info("Start process to add new Institues in DB");
		instituteProcessor.saveInstitute(institutes);
		return new GenericResponseHandlers.Builder().setMessage("Institutes added successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> update(final String userId, final String instituteId, final InstituteRequestDto institute) throws Exception {
		log.info("Start process to update existing Institue having instituteId = {}",instituteId);
		instituteProcessor.updateInstitute(userId, instituteId, institute);
		return new GenericResponseHandlers.Builder().setMessage("Institutes updated successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getAllInstitute(final Integer pageNumber, final Integer pageSize) throws Exception {
		log.info("Start process to fetch all Institutes from DB based on pagination");
		PaginationResponseDto paginationResponseDto = instituteProcessor.getAllInstitute(pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(paginationResponseDto).setMessage("Institute displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@Override
	public ResponseEntity<?> getAllInstitute(final String searchKey, final Integer pageNumber, final Integer pageSize) throws Exception {
		log.info("Start process to fetch all Institutes from DB based on pagination and searchKeyword");
		PaginationResponseDto paginationResponseDto = instituteProcessor.autoSearch(pageNumber, pageSize, searchKey);
		return new GenericResponseHandlers.Builder().setData(paginationResponseDto).setMessage("Institute displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@Override
	public ResponseEntity<?> get(final String userId, final String instituteId)
			throws Exception {
		log.info("Start process to fetch Institutes from DB for instituteId = {}", instituteId);
		InstituteRequestDto instituteRequestDtos = instituteProcessor.getById(userId, instituteId);
		return new GenericResponseHandlers.Builder().setData(instituteRequestDtos)
				.setMessage("Institute details get successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> searchInstitute(final String searchText) throws Exception {
		log.info("Start process to search Institutes from DB for searchKeyword = {}", searchText);
		List<InstituteGetRequestDto> instituteGetRequestDtos = instituteProcessor.searchInstitute(searchText);
		return new GenericResponseHandlers.Builder().setData(instituteGetRequestDtos).setMessage("Institute displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@Override
	public ResponseEntity<?> instituteFilter(final InstituteFilterDto instituteFilterDto) throws Exception {
		log.info("Start process to fetch institutes from DB based on passed filters");
		PaginationResponseDto instituteResponseDto = instituteProcessor.instituteFilter(instituteFilterDto);
		return new GenericResponseHandlers.Builder().setData(instituteResponseDto)
				.setMessage("Institute displayed successfully").setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getAllCategoryType() throws Exception {
		log.info("Start process to fetch all InstituteCategories from DB");
		List<InstituteCategoryType> categoryTypes = instituteProcessor.getAllCategories();
		return new GenericResponseHandlers.Builder().setData(categoryTypes)
				.setMessage("Institute Category Type fetched successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getAllInstituteType() throws Exception {
		log.info("Start process to fetch all InstituteTypes from DB");
		List<InstituteTypeDto> instituteTypes = instituteTypeProcessor.getAllInstituteType();
		return new GenericResponseHandlers.Builder().setData(instituteTypes)
				.setMessage("InstituteTypes fetched successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> delete(final String instituteId) throws ValidationException {
		log.info("Start process to inactive existing Institute for instituteId = {}",instituteId);
		instituteProcessor.deleteInstitute(instituteId);
		return new GenericResponseHandlers.Builder().setMessage("Institute deleted successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstituteImage(final String instituteId) throws Exception {
		log.info("Start process to fetch all InstituteImages for instituteId = {}", instituteId);
		List<StorageDto> storageDTOList = storageHandler.getStorages(instituteId, EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.IMAGES);
		return new GenericResponseHandlers.Builder().setData(storageDTOList).setMessage("Get Image List successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getTotalCourseForInstitute(final String instituteId) throws ValidationException {
		log.info("Start process to get total count of courses for institute having instituteId = {}", instituteId);
		Integer courseCount = instituteProcessor.getTotalCourseCountForInstitute(instituteId);
		return new GenericResponseHandlers.Builder().setData(courseCount).setMessage("Course Count returned successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getHistoryOfDomesticRanking(final String instituteId) throws ValidationException {
		log.info("Start process to get history of domesticRanking for institute having instituteId = {}", instituteId);
		List<InstituteDomesticRankingHistoryDto> instituteDomesticRankingHistory = instituteProcessor.getHistoryOfDomesticRanking(instituteId);
		return new GenericResponseHandlers.Builder().setData(instituteDomesticRankingHistory).setMessage("Get institute domestic ranking successfully")
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getHistoryOfWorldRanking(final String instituteId) throws ValidationException {
		log.info("Start process to get history of worldRanking for institute having instituteId = {}", instituteId);
		List<InstituteWorldRankingHistoryDto> instituteWorldRankingHistory = instituteProcessor.getHistoryOfWorldRanking(instituteId);
		return new GenericResponseHandlers.Builder().setData(instituteWorldRankingHistory).setMessage("Get institute world ranking successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getDomesticRanking(final List<String> courseIds) throws ValidationException {
		log.info("Start process to get domesticRanking for courses");
		Map<String, Integer> instituteIdDomesticRanking = instituteProcessor.getDomesticRanking(courseIds);
		return new GenericResponseHandlers.Builder().setData(instituteIdDomesticRanking).setMessage("Domestic Ranking Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getNearestInstituteList(final AdvanceSearchDto courseSearchDto) throws Exception {
		log.info("Start process to fetch nearest Institutes from DB based on filters");
		NearestInstituteDTO nearestInstituteDTOs = instituteProcessor.getNearestInstituteList(courseSearchDto);
		return new GenericResponseHandlers.Builder().setData(nearestInstituteDTOs).setMessage("Institute Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getDistinctInstututes(final Integer pageNumber, final Integer pageSize, final String name) throws Exception {
		log.debug("Inside getDistinctInstututes() method");
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		log.info("Getting total count of institute having instituteName = {}", name);
		int totalCount = instituteProcessor.getDistinctInstituteCount(name);
		log.info("Fetching distinct institutes from DB based on pagination for instituteName = {}", name);
		List<InstituteResponseDto> instituteList = instituteProcessor.getDistinctInstituteList(startIndex, pageSize, name);
		log.info("Calculating pagination based on pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		paginationResponseDto.setResponse(instituteList);
		paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		paginationResponseDto.setTotalCount(totalCount);
		paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		return new GenericResponseHandlers.Builder().setData(paginationResponseDto).setMessage("Institute Displayed Successfully.")
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getBoundedInstituteList(final Integer pageNumber, final Integer pageSize, 
			List<LatLongDto> latLongDto) throws ValidationException {
		log.info("Start process to fetch bounded Institute list based on passed latitude and longitude");
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		NearestInstituteDTO nearestInstituteList = instituteProcessor.getInstitutesUnderBoundRegion(startIndex, pageSize, latLongDto);
		return new GenericResponseHandlers.Builder().setData(nearestInstituteList)
				.setMessage("Institute Displayed Successfully").setStatus(HttpStatus.OK).create();
	}
	
	@GetMapping(value = "/institute/pageNumber/{pageNumber}/pageSize/{pageSize}/{countryName}")
	public ResponseEntity<?> getInstituteByCountryName(Integer pageNumber, Integer pageSize,
			String countryName) throws NotFoundException {
		NearestInstituteDTO instituteResponse = instituteProcessor.getInstituteByCountryName(countryName, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(instituteResponse)
				.setMessage("Institutes displayed successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstituteCampuses(String instituteId) throws NotFoundException {
		List<InstituteCampusDto> instituteCampuses = instituteProcessor.getInstituteCampuses(instituteId);
		return new GenericResponseHandlers.Builder().setData(instituteCampuses)
				.setMessage("Institute campuses displayed successfully").setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getInstituteFaculties(String instituteId) throws NotFoundException {
		List<InstituteFacultyDto> instituteFaculties = instituteProcessor.getInstituteFaculties(instituteId);
		return new GenericResponseHandlers.Builder().setData(instituteFaculties)
				.setMessage("Institute faculties displayed successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstituteCourseScholarshipAndFacultyCount(String instituteId) throws NotFoundException {
		CourseScholarshipAndFacultyCountDto data = instituteProcessor
				.getInstituteCourseScholarshipAndFacultyCount(instituteId);
		return new GenericResponseHandlers.Builder().setData(data)
				.setMessage("Institute Course,Scholarship and faculty count displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstitutesByIdList(List<String> instituteIds) throws NotFoundException, InvokeException, Exception {
		if (ObjectUtils.isEmpty(instituteIds)) {
			log.error("institute_ids must not be empty");
			throw new ValidationException("institute_ids must not be empty");
		}
		List<InstituteResponseDto> instituteList = instituteProcessor.getInstitutesByIdList(instituteIds);
		return new GenericResponseHandlers.Builder().setData(instituteList).setMessage("Institute Displayed Successfully.")
				.setStatus(HttpStatus.OK).create();
	}	
}
