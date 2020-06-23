package com.seeka.app.controller.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteWorldRankingHistory;
import com.seeka.app.bean.Service;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteDomesticRankingHistoryDto;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteGetRequestDto;
import com.seeka.app.dto.InstituteRequestDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteTypeDto;
import com.seeka.app.dto.LatLongDto;
import com.seeka.app.dto.NearestInstituteDTO;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.dto.PaginationResponseDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.endpoint.InstituteInterface;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.IInstituteServiceDetailsService;
import com.seeka.app.service.IInstituteTypeService;
import com.seeka.app.service.IServiceDetailsService;
import com.seeka.app.service.IStorageService;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.PaginationUtil;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController("instituteControllerV1")
public class InstituteController implements InstituteInterface {

	@Autowired
	private IInstituteService instituteService;

	@Autowired
	private IInstituteTypeService instituteTypeService;

	@Autowired
	private IServiceDetailsService serviceDetailsService;

	@Autowired
	private IInstituteServiceDetailsService instituteServiceDetailsService;

	@Autowired
	private IStorageService iStorageService;

	@Override
	public ResponseEntity<?> saveInstituteType(final InstituteTypeDto instituteTypeDto) throws Exception {
		instituteTypeService.save(instituteTypeDto);
		return new GenericResponseHandlers.Builder().setMessage("InstituteType Added successfully")
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getInstituteTypeByCountry(String countryName) throws Exception {
		List<InstituteTypeDto> listOfInstituteTypes = instituteTypeService.getInstituteTypeByCountryName(countryName);
		return new GenericResponseHandlers.Builder().setData(listOfInstituteTypes).setMessage("Institute types fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> saveService(final String instituteTypeId) throws Exception {
		List<Service> list = new ArrayList<>();
		String createdBy = "AUTO";
		Date createdOn = new Date();

		Service serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Visa Work Benefits");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Visa Work Benefits");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Employment and career development");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Employment and career development");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Counselling – personal and academic");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Counselling – personal and academic");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Study Library Support");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Study Library Support");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Health services");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Health services");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Disability Support");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Disability Support");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Childcare Centre");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Childcare Centre");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Cultural inclusion/anti-racism programs");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Cultural inclusion/anti-racism programs");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Technology Services");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Technology Services");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Accommodation");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Accommodation");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Medical");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Medical");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Legal Services");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Legal Services");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Accounting Services");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Accounting Services");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Bus");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Bus");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Train");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Train");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Airport Pickup");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Airport Pickup");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Swimming pool");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Swimming pool");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Sports Center");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Sports Center");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Sport Teams");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Sport Teams");
		list.add(serviceObj);

		serviceObj = new Service();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Housing Services");
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Housing Services");
		list.add(serviceObj);

		for (Service serviceDetails : list) {
			try {
				serviceDetailsService.save(serviceDetails);
			} catch (Exception e) {
				log.error("Exception while adding services in DB having exception = "+e);
			}
		}
		return new GenericResponseHandlers.Builder().setMessage("Services Added successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getAllInstituteService() throws Exception {
		List<Service> serviceDetailsList = serviceDetailsService.getAll();
		return new GenericResponseHandlers.Builder().setData(serviceDetailsList).setMessage("Services displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getAllServicesByInstitute(final String id) throws Exception {
		List<String> serviceNames = instituteServiceDetailsService.getAllServices(id);
		return new GenericResponseHandlers.Builder().setData(serviceNames).setMessage("Services displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> instituteSearch(final CourseSearchDto request) throws Exception {
		return getInstitutesBySearchFilters(request, null, null, null, null, null, null, null, null, null);
	}

	@Override
	public ResponseEntity<?> instituteSearch(final Integer pageNumber, final Integer pageSize, final List<String> countryNames, 
			final List<String> facultyIds,final List<String> levelIds, final String cityName, final String instituteType, final Boolean isActive,
			final Date updatedOn, final Integer fromWorldRanking, final Integer toWorldRanking, final String sortByField, final String sortByType, 
			final String searchKeyword, final Double latitutde, final Double longitude) throws ValidationException {
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
		List<InstituteResponseDto> instituteList = instituteService.getAllInstitutesByFilter(request, sortByField, sortByType, searchKeyword, startIndex,
				cityId, instituteTypeId, isActive, updatedOn, fromWorldRanking, toWorldRanking);
		if(!CollectionUtils.isEmpty(instituteList)) {
			log.info("Institutes fetched from DB, now iterating data to call Storage service");
			instituteList.stream().forEach(instituteResponseDto -> {
				try {
					log.info("Calling Storage service to get imageCategories for Institute");
					List<StorageDto> storageDTOList = iStorageService.getStorageInformation(instituteResponseDto.getId(), ImageCategory.INSTITUTE.toString(), null, "en");
					instituteResponseDto.setStorageList(storageDTOList);
				} catch (ValidationException e) {
					log.error("Error invoking Storage service having exception ="+e);
				}
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
		int totalCount = instituteService.getCountOfInstitute(request, searchKeyword, cityId, instituteTypeId, isActive, updatedOn, fromWorldRanking,
				toWorldRanking);
		log.info("Calling pagination class to calculate pagination based on startIndex, pageSixe and totalCount of institutes");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, request.getMaxSizePerPage(), totalCount);
		log.info("Adding values in paginationResponse DTO and returning final response");
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		paginationResponseDto.setInstitutes(instituteList);
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
		Boolean showMore = false;
		Integer maxCount = 0, totalCount = 0;
		if (request.getPageNumber() > PaginationUtil.courseResultPageMaxSize) {
			log.error("Maximum course limit per is " + PaginationUtil.courseResultPageMaxSize);
			throw new ValidationException("Maximum course limit per is " + PaginationUtil.courseResultPageMaxSize);
		}
		if (null == request.getUserId()) {
			log.error("UserId is required");
			throw new ValidationException("UserId is required");
		}
		log.info("Calling DAO layer to get all institutes based on filters");
		List<InstituteResponseDto> instituteResponseDtoList = instituteService.getAllInstitutesByFilter(request, null, null, null,
				null, null, null, null, null, null, null);
		if(!CollectionUtils.isEmpty(instituteResponseDtoList)) {
			log.info("Filtered institutes coming from DB, hence start iterating");
			totalCount = instituteResponseDtoList.get(0).getTotalCount();
			maxCount = instituteResponseDtoList.size();
			instituteResponseDtoList.stream().forEach(instituteResponseDto -> {
				try {
					log.info("Invoking storage service to fetch images for institutes");
					List<StorageDto> storageDTOList = iStorageService.getStorageInformation(instituteResponseDto.getId(), ImageCategory.INSTITUTE.toString(), null, "en");
					instituteResponseDto.setStorageList(storageDTOList);
				} catch (ValidationException e) {
					log.error("Error invoking Storage service having exception "+e);
				}
			});
		}
		if (request.getMaxSizePerPage() == maxCount) {
			log.info("if maxSize and max count are equal then showMore is visible");
			showMore = true;
		}
		log.info("Adding values in PaginationDTO class and return in final response");
		PaginationDto paginationDto = new PaginationDto(totalCount, showMore,instituteResponseDtoList);
		return new GenericResponseHandlers.Builder().setData(paginationDto).setMessage("Recommended Institutes displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstituteByCityId(final String cityName) throws Exception {
		List<InstituteResponseDto> institutes = instituteService.getInstitudeByCityId(cityName);
		return new GenericResponseHandlers.Builder().setData(institutes).setMessage("Institutes displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstituteByListOfCityId(final String cityName) throws Exception {
		List<InstituteResponseDto> institutes = instituteService.getInstituteByListOfCityId(cityName);
		return new GenericResponseHandlers.Builder().setData(institutes).setMessage("Institutes displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> save(final List<InstituteRequestDto> institutes) throws Exception {
		instituteService.saveInstitute(institutes);
		return new GenericResponseHandlers.Builder().setMessage("Institutes added successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> update(final String id, final List<InstituteRequestDto> institute) throws Exception {
		instituteService.updateInstitute(institute, id);
		return new GenericResponseHandlers.Builder().setMessage("Institutes updated successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getAllInstitute(final Integer pageNumber, final Integer pageSize) throws Exception {
		PaginationResponseDto paginationResponseDto = instituteService.getAllInstitute(pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(paginationResponseDto).setMessage("Institute displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@Override
	public ResponseEntity<?> getAllInstitute(final String searchKey, final Integer pageNumber, final Integer pageSize) throws Exception {
		PaginationResponseDto paginationResponseDto = instituteService.autoSearch(pageNumber, pageSize, searchKey);
		return new GenericResponseHandlers.Builder().setData(paginationResponseDto).setMessage("Institute displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@Override
	public ResponseEntity<?> get(final String id) throws ValidationException {
		List<InstituteRequestDto> instituteRequestDtos = instituteService.getById(id);
		return new GenericResponseHandlers.Builder().setData(instituteRequestDtos).setMessage("Institute details get successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@Override
	public ResponseEntity<?> searchInstitute(final String searchText) throws Exception {
		List<InstituteGetRequestDto> instituteGetRequestDtos = instituteService.searchInstitute(searchText);
		return new GenericResponseHandlers.Builder().setData(instituteGetRequestDtos).setMessage("Institute displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@Override
	public ResponseEntity<?> getAllInstitute() throws Exception {
		List<Institute> institutes = instituteService.getAll();
		return new GenericResponseHandlers.Builder().setData(institutes).setMessage("Institute displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@Override
	public ResponseEntity<?> instituteFilter(final InstituteFilterDto instituteFilterDto) throws Exception {
		PaginationResponseDto instituteResponseDto = instituteService.instituteFilter(instituteFilterDto);
		return new GenericResponseHandlers.Builder().setData(instituteResponseDto)
				.setMessage("Institute displayed successfully").setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getAllCategoryType() throws Exception {
		List<InstituteCategoryType> categoryTypes = instituteService.getAllCategories();
		return new GenericResponseHandlers.Builder().setData(categoryTypes)
				.setMessage("Institute Category Type fetched successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getAllInstituteType() throws Exception {
		List<InstituteTypeDto> instituteTypes = instituteTypeService.getAllInstituteType();
		return new GenericResponseHandlers.Builder().setData(instituteTypes)
				.setMessage("InstituteTypes fetched successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> delete(final String instituteId) throws ValidationException {
		instituteService.deleteInstitute(instituteId);
		return new GenericResponseHandlers.Builder().setMessage("Institute deleted successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstituteImage(final String instituteId) throws Exception {
		List<StorageDto> storageDTOList = iStorageService.getStorageInformation(instituteId, ImageCategory.INSTITUTE.toString(), null, "en");
		return new GenericResponseHandlers.Builder().setData(storageDTOList).setMessage("Get Image List successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getTotalCourseForInstitute(final String instituteId) throws ValidationException {
		Integer courseCount = instituteService.getTotalCourseCountForInstitute(instituteId);
		return new GenericResponseHandlers.Builder().setData(courseCount).setMessage("Course Count returned successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getHistoryOfDomesticRanking(final String instituteId) throws ValidationException {
		List<InstituteDomesticRankingHistoryDto> instituteDomesticRankingHistory = instituteService.getHistoryOfDomesticRanking(instituteId);
		return new GenericResponseHandlers.Builder().setData(instituteDomesticRankingHistory).setMessage("Get institute domestic ranking successfully")
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getHistoryOfWorldRanking(final String instituteId) throws ValidationException {
		InstituteWorldRankingHistory instituteWorldRankingHistory = instituteService.getHistoryOfWorldRanking(instituteId);
		return new GenericResponseHandlers.Builder().setData(instituteWorldRankingHistory).setMessage("Get institute world ranking successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getDomesticRanking(final List<String> courseIds) throws ValidationException {
		Map<String, Integer> instituteIdDomesticRanking = instituteService.getDomesticRanking(courseIds);
		return new GenericResponseHandlers.Builder().setData(instituteIdDomesticRanking).setMessage("Domestic Ranking Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getNearestInstituteList(final AdvanceSearchDto courseSearchDto) throws Exception {
		NearestInstituteDTO nearestInstituteDTOs = instituteService.getNearestInstituteList(courseSearchDto);
		return new GenericResponseHandlers.Builder().setData(nearestInstituteDTOs).setMessage("Institute Displayed Successfully.")
				.setStatus(HttpStatus.OK).create();
	}
	
	@Override
	public ResponseEntity<?> getDistinctInstututes(final Integer pageNumber, final Integer pageSize, final String name) throws Exception {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		int totalCount = instituteService.getDistinctInstituteCount(name);
		List<InstituteResponseDto> instituteList = instituteService.getDistinctInstituteList(startIndex, pageSize, name);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		paginationResponseDto.setInstitutes(instituteList);
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
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		NearestInstituteDTO nearestInstituteList = instituteService.getInstitutesUnderBoundRegion(startIndex, pageSize, latLongDto);
		return new GenericResponseHandlers.Builder().setData(nearestInstituteList)
				.setMessage("Institute Displayed Successfully").setStatus(HttpStatus.OK).create();
	}

}
