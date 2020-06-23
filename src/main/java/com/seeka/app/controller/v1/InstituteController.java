package com.seeka.app.controller.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteDomesticRankingHistory;
import com.seeka.app.bean.InstituteWorldRankingHistory;
import com.seeka.app.bean.Service;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseSearchDto;
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

@RestController("instituteControllerV1")
@RequestMapping("/api/v1/institute")
@CommonsLog
public class InstituteController {

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

	@RequestMapping(value = "/type", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> saveInstituteType(@Valid @RequestBody final InstituteTypeDto instituteTypeDto) throws Exception {
		instituteTypeService.save(instituteTypeDto);
		return new GenericResponseHandlers.Builder().setMessage("InstituteType Added successfully")
				.setStatus(HttpStatus.OK).create();
	}
	
	@RequestMapping(value = "/type", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getInstituteTypeByCountry(@RequestParam(name = "country_name") String countryName) throws Exception {
		List<InstituteTypeDto> listOfInstituteTypes = instituteTypeService.getInstituteTypeByCountryName(countryName);
		return new GenericResponseHandlers.Builder().setData(listOfInstituteTypes).setMessage("Institute types fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/service/{instituteTypeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> saveService(@PathVariable final String instituteTypeId) throws Exception {
		Map<String, Object> response = new HashMap<>();
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
				e.printStackTrace();
			}
		}
		response.put("status", 1);
		response.put("message", "Success.!");
		response.put("serviceList", list);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/service/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllInstituteService() throws Exception {
		List<Service> serviceDetailsList = serviceDetailsService.getAll();
		return new GenericResponseHandlers.Builder().setData(serviceDetailsList).setMessage("Services displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/{id}/service", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllServicesByInstitute(@Valid @PathVariable final String id) throws Exception {
		List<String> serviceNames = instituteServiceDetailsService.getAllServices(id);
		return new GenericResponseHandlers.Builder().setData(serviceNames).setMessage("Services displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> instituteSearch(@RequestBody final CourseSearchDto request) throws Exception {
		return getInstitutesBySearchFilters(request, null, null, null, null, null, null, null, null, null);
	}

	@RequestMapping(value = "/search/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> instituteSearch(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final List<String> countryNames, @RequestParam(required = false) final List<String> facultyIds,
			@RequestParam(required = false) final List<String> levelIds, @RequestParam(required = false) final String cityName,
			@RequestParam(required = false) final String instituteType, @RequestParam(required = false) final Boolean isActive,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date updatedOn,
			@RequestParam(required = false) final Integer fromWorldRanking, @RequestParam(required = false) final Integer toWorldRanking,
			@RequestParam(required = false) final String sortByField, @RequestParam(required = false) final String sortByType,
			@RequestParam(required = false) final String searchKeyword,
			@RequestParam(required = false) final Double latitutde, @RequestParam(required = false) final Double longitude) throws ValidationException {
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

	@RequestMapping(value = "/recommended", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getAllRecommendedInstitutes(@RequestBody final CourseSearchDto request) throws Exception {
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

	@RequestMapping(value = "/city/{cityName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getInstituteByCityId(@Valid @PathVariable final String cityName) throws Exception {
		List<InstituteResponseDto> institutes = instituteService.getInstitudeByCityId(cityName);
		return new GenericResponseHandlers.Builder().setData(institutes).setMessage("Institutes displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/multiple/city/{cityName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getInstituteByListOfCityId(@Valid @PathVariable final String cityName) throws Exception {
		List<InstituteResponseDto> institutes = instituteService.getInstituteByListOfCityId(cityName);
		return new GenericResponseHandlers.Builder().setData(institutes).setMessage("Institutes displayed successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> save(@Valid @RequestBody final List<InstituteRequestDto> institutes) throws Exception {
		instituteService.saveInstitute(institutes);
		return new GenericResponseHandlers.Builder().setMessage("Institutes added successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> update(@Valid @PathVariable final String id, @RequestBody final List<InstituteRequestDto> institute) throws Exception {
		instituteService.updateInstitute(institute, id);
		return new GenericResponseHandlers.Builder().setMessage("Institutes updated successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllInstitute(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception {
		PaginationResponseDto paginationResponseDto = instituteService.getAllInstitute(pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(paginationResponseDto).setMessage("Institute displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@RequestMapping(value = "/autoSearch/{searchKey}/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllInstitute(@PathVariable final String searchKey, @PathVariable final Integer pageNumber, 
				@PathVariable final Integer pageSize) throws Exception {
		PaginationResponseDto paginationResponseDto = instituteService.autoSearch(pageNumber, pageSize, searchKey);
		return new GenericResponseHandlers.Builder().setData(paginationResponseDto).setMessage("Institute displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable final String id) throws ValidationException {
		List<InstituteRequestDto> instituteRequestDtos = instituteService.getById(id);
		return new GenericResponseHandlers.Builder().setData(instituteRequestDtos).setMessage("Institute details get successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@RequestMapping(value = "search/{searchText}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchInstitute(@Valid @PathVariable final String searchText) throws Exception {
		List<InstituteGetRequestDto> instituteGetRequestDtos = instituteService.searchInstitute(searchText);
		return new GenericResponseHandlers.Builder().setData(instituteGetRequestDtos).setMessage("Institute displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@RequestMapping(value = "all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllInstitute() throws Exception {
		List<Institute> institutes = instituteService.getAll();
		return new GenericResponseHandlers.Builder().setData(institutes).setMessage("Institute displayed successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> instituteFilter(@RequestBody final InstituteFilterDto instituteFilterDto) throws Exception {
		PaginationResponseDto instituteResponseDto = instituteService.instituteFilter(instituteFilterDto);
		return new GenericResponseHandlers.Builder().setData(instituteResponseDto)
				.setMessage("Institute displayed successfully").setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "allCategoryType", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllCategoryType() throws Exception {
		List<InstituteCategoryType> categoryTypes = instituteService.getAllCategories();
		return new GenericResponseHandlers.Builder().setData(categoryTypes)
				.setMessage("Institute Category Type fetched successfully").setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "type", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllInstituteType() throws Exception {
		List<InstituteTypeDto> instituteTypes = instituteTypeService.getAllInstituteType();
		return new GenericResponseHandlers.Builder().setData(instituteTypes)
				.setMessage("InstituteTypes fetched successfully").setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/{instituteId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable final String instituteId) throws ValidationException {
		instituteService.deleteInstitute(instituteId);
		return new GenericResponseHandlers.Builder().setMessage("Institute deleted successfully").setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/images/{instituteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getInstituteImage(@PathVariable final String instituteId) throws Exception {
		List<StorageDto> storageDTOList = iStorageService.getStorageInformation(instituteId, ImageCategory.INSTITUTE.toString(), null, "en");
		return new GenericResponseHandlers.Builder().setData(storageDTOList).setMessage("Get Image List successfully").setStatus(HttpStatus.OK).create();
	}

	@GetMapping(value = "/totalCourseCount", produces = "application/json")
	public ResponseEntity<?> getTotalCourseForInstitute(@RequestParam(value = "instituteId", required = true) final String instituteId)
			throws ValidationException {
		Integer courseCount = instituteService.getTotalCourseCountForInstitute(instituteId);
		return new GenericResponseHandlers.Builder().setData(courseCount).setMessage("Course Count returned successfully").setStatus(HttpStatus.OK).create();
	}

	@GetMapping(value = "/history/domestic/ranking", produces = "application/json")
	public ResponseEntity<?> getHistoryOfDomesticRanking(@RequestParam(value = "instituteId", required = true) final String instituteId)
			throws ValidationException {
		InstituteDomesticRankingHistory instituteDomesticRankingHistory = instituteService.getHistoryOfDomesticRanking(instituteId);
		return new GenericResponseHandlers.Builder().setData(instituteDomesticRankingHistory).setMessage("Get institute domestic ranking successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@GetMapping(value = "/history/world/ranking", produces = "application/json")
	public ResponseEntity<?> getHistoryOfWorldRanking(@RequestParam(value = "instituteId", required = true) final String instituteId)
			throws ValidationException {
		InstituteWorldRankingHistory instituteWorldRankingHistory = instituteService.getHistoryOfWorldRanking(instituteId);
		return new GenericResponseHandlers.Builder().setData(instituteWorldRankingHistory).setMessage("Get institute world ranking successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@PostMapping(value = "/domesticRankingForCourse", produces = "application/json")
	public ResponseEntity<?> getDomesticRanking(@RequestBody final List<String> courseIds) throws ValidationException {
		Map<String, Integer> instituteIdDomesticRanking = instituteService.getDomesticRanking(courseIds);
		return new GenericResponseHandlers.Builder().setData(instituteIdDomesticRanking).setMessage("Domestic Ranking Displayed Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@PostMapping(value = "/nearest", produces = "application/json")
	public ResponseEntity<?> getNearestInstituteList(@RequestBody final AdvanceSearchDto courseSearchDto) throws Exception {
		NearestInstituteDTO nearestInstituteDTOs = instituteService.getNearestInstituteList(courseSearchDto);
		return new GenericResponseHandlers.Builder().setData(nearestInstituteDTOs).setMessage("Institute Displayed Successfully.")
				.setStatus(HttpStatus.OK).create();
	}
	
	
	@RequestMapping(value = "/names/distinct/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDistinctInstututes(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String name)
			throws Exception {
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
	
	@PostMapping(value = "/bounded/area/pageNumber/{pageNumber}/pageSize/{pageSize}",  produces = "application/json")
	public ResponseEntity<?> getBoundedInstituteList(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize, 
			@RequestBody List<LatLongDto> latLongDto) throws ValidationException {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		NearestInstituteDTO nearestInstituteList = instituteService.getInstitutesUnderBoundRegion(startIndex, pageSize, latLongDto);
		return new GenericResponseHandlers.Builder().setData(nearestInstituteList)
				.setMessage("Institute Displayed Successfully").setStatus(HttpStatus.OK).create();
	}

}
