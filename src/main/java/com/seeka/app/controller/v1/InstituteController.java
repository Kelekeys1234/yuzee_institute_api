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
import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.InstituteWorldRankingHistory;
import com.seeka.app.bean.Service;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteRequestDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.NearestInstituteDTO;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.IInstituteServiceDetailsService;
import com.seeka.app.service.IInstituteTypeService;
import com.seeka.app.service.IServiceDetailsService;
import com.seeka.app.service.IStorageService;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@RestController("instituteControllerV1")
@RequestMapping("/api/v1/institute")
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
	public ResponseEntity<?> saveInstituteType(@Valid @RequestBody final InstituteType instituteTypeObj) throws Exception {
		Map<String, Object> response = new HashMap<>(3);
		instituteTypeService.save(instituteTypeObj);
		response.put("message", "Institute type saved successfully");
		response.put("status", HttpStatus.OK.value());
		response.put("data", instituteTypeObj);
		return ResponseEntity.accepted().body(response);
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
		Map<String, Object> response = new HashMap<>();
		List<Service> list = serviceDetailsService.getAll();
		response.put("status", 1);
		response.put("message", "Success.!");
		response.put("serviceList", list);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/{id}/service", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllServicesByInstitute(@Valid @PathVariable final String id) throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<String> serviceNames = instituteServiceDetailsService.getAllServices(id);
		if (serviceNames != null && !serviceNames.isEmpty()) {
			response.put("message", "Service fetched successfully");
			response.put("status", HttpStatus.OK.value());
		} else {
			response.put("message", "Service not found");
			response.put("status", HttpStatus.NOT_FOUND.value());
		}
		response.put("data", serviceNames);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> instituteSearch(@RequestBody final CourseSearchDto request) throws Exception {
		return getInstitutesBySearchFilters(request, null, null, null, null, null, null, null, null, null, null);
	}

	@RequestMapping(value = "/search/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> instituteSearch(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final List<String> countryIds, @RequestParam(required = false) final List<String> facultyIds,
			@RequestParam(required = false) final List<String> levelIds, @RequestParam(required = false) final String cityId,
			@RequestParam(required = false) final String instituteTypeId, @RequestParam(required = false) final Boolean isActive,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date updatedOn,
			@RequestParam(required = false) final Integer fromWorldRanking, @RequestParam(required = false) final Integer toWorldRanking,
			@RequestParam(required = false) final String sortByField, @RequestParam(required = false) final String sortByType,
			@RequestParam(required = false) final String searchKeyword, @RequestParam(required = false) final String campusType) throws ValidationException {
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setCountryIds(countryIds);
		courseSearchDto.setFacultyIds(facultyIds);
		courseSearchDto.setLevelIds(levelIds);
		courseSearchDto.setPageNumber(pageNumber);
		courseSearchDto.setMaxSizePerPage(pageSize);
		return getInstitutesBySearchFilters(courseSearchDto, sortByField, sortByType, searchKeyword, cityId, instituteTypeId, isActive, updatedOn,
				fromWorldRanking, toWorldRanking, campusType);
	}

	private ResponseEntity<?> getInstitutesBySearchFilters(final CourseSearchDto request, final String sortByField, final String sortByType,
			final String searchKeyword, final String cityId, final String instituteTypeId, final Boolean isActive, final Date updatedOn,
			final Integer fromWorldRanking, final Integer toWorldRanking, final String campusType) throws ValidationException {
		List<String> countryIds = request.getCountryIds();
		if (null == countryIds || countryIds.isEmpty()) {
			countryIds = new ArrayList<>();
		}
		int startIndex = PaginationUtil.getStartIndex(request.getPageNumber(), request.getMaxSizePerPage());
		List<InstituteResponseDto> instituteList = instituteService.getAllInstitutesByFilter(request, sortByField, sortByType, searchKeyword, startIndex,
				cityId, instituteTypeId, isActive, updatedOn, fromWorldRanking, toWorldRanking, campusType);
		for (InstituteResponseDto instituteResponseDto : instituteList) {
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(instituteResponseDto.getId(), ImageCategory.INSTITUTE.toString(), null, "en");
			instituteResponseDto.setStorageList(storageDTOList);
		}

		int totalCount = instituteService.getCountOfInstitute(request, searchKeyword, cityId, instituteTypeId, isActive, updatedOn, fromWorldRanking,
				toWorldRanking, campusType);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, request.getMaxSizePerPage(), totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Institute List successfully");
		responseMap.put("data", instituteList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/recommended", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getAllRecommendedInstitutes(@RequestBody final CourseSearchDto request) throws Exception {
		Map<String, Object> response = new HashMap<>();
		ErrorDto errorDto = null;
		if (request.getPageNumber() > PaginationUtil.courseResultPageMaxSize) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Maximum course limit per is " + PaginationUtil.courseResultPageMaxSize);
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}

		if (null == request.getUserId()) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid user data.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}

		List<String> countryIds = request.getCountryIds();
		if (null == countryIds || countryIds.isEmpty()) {
			countryIds = new ArrayList<>();
		}

		List<InstituteResponseDto> instituteResponseDtoList = instituteService.getAllInstitutesByFilter(request, null, null, null, null, null, null, null, null,
				null, null, null);
		for (InstituteResponseDto instituteResponseDto : instituteResponseDtoList) {
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(instituteResponseDto.getId(), ImageCategory.INSTITUTE.toString(), null, "en");
			instituteResponseDto.setStorageList(storageDTOList);
		}
		Integer maxCount = 0, totalCount = 0;
		if (null != instituteResponseDtoList && !instituteResponseDtoList.isEmpty()) {
			totalCount = instituteResponseDtoList.get(0).getTotalCount();
			maxCount = instituteResponseDtoList.size();
		}
		boolean showMore;
		if (request.getMaxSizePerPage() == maxCount) {
			showMore = true;
		} else {
			showMore = false;
		}
		response.put("status", 1);
		response.put("message", "Success.!");
		response.put("paginationObj", new PaginationDto(totalCount, showMore));
		response.put("instituteList", instituteResponseDtoList);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/city/{cityId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getInstituteByCityId(@Valid @PathVariable final String cityId) throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<InstituteResponseDto> institutes = instituteService.getInstitudeByCityId(cityId);
		if (institutes != null && !institutes.isEmpty()) {
			response.put("message", "Institute fetched successfully");
			response.put("status", HttpStatus.OK.value());
		} else {
			response.put("message", IConstant.INSTITUDE_NOT_FOUND);
			response.put("status", HttpStatus.NOT_FOUND.value());
		}
		response.put("data", institutes);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/multiple/city/{cityId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getInstituteByListOfCityId(@Valid @PathVariable final String cityId) throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<InstituteResponseDto> institutes = instituteService.getInstituteByListOfCityId(cityId);
		if (institutes != null && !institutes.isEmpty()) {
			response.put("message", "Institute fetched successfully");
			response.put("status", HttpStatus.OK.value());
		} else {
			response.put("message", IConstant.INSTITUDE_NOT_FOUND);
			response.put("status", HttpStatus.NOT_FOUND.value());
		}
		response.put("institutes", institutes);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> save(@Valid @RequestBody final List<InstituteRequestDto> institutes) throws Exception {
		return ResponseEntity.accepted().body(instituteService.save(institutes));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> update(@Valid @PathVariable final String id, @RequestBody final List<InstituteRequestDto> institute) throws Exception {
		return ResponseEntity.accepted().body(instituteService.update(institute, id));
	}

	@RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllInstitute(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception {
		return ResponseEntity.accepted().body(instituteService.getAllInstitute(pageNumber, pageSize));
	}

	@RequestMapping(value = "/autoSearch/{searchKey}/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllInstitute(@PathVariable final String searchKey, @PathVariable final Integer pageNumber, @PathVariable final Integer pageSize)
			throws Exception {
		return ResponseEntity.accepted().body(instituteService.autoSearch(pageNumber, pageSize, searchKey));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable final String id) throws ValidationException {
		List<InstituteRequestDto> instituteRequestDtos = instituteService.getById(id);
		return new GenericResponseHandlers.Builder().setData(instituteRequestDtos).setMessage("Institute details get successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@RequestMapping(value = "search/{searchText}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchInstitute(@Valid @PathVariable final String searchText) throws Exception {
		return ResponseEntity.accepted().body(instituteService.searchInstitute(searchText));
	}

	@RequestMapping(value = "all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllInstitute() throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<Institute> institutes = instituteService.getAll();
		if (institutes != null && !institutes.isEmpty()) {
			response.put("message", "Institute fetched successfully");
			response.put("status", HttpStatus.OK.value());
		} else {
			response.put("message", IConstant.INSTITUDE_NOT_FOUND);
			response.put("status", HttpStatus.NOT_FOUND.value());
		}
		response.put("institutes", institutes);
		return ResponseEntity.accepted().body(response);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> instituteFilter(@RequestBody final InstituteFilterDto instituteFilterDto) throws Exception {
		return ResponseEntity.ok().body(instituteService.instituteFilter(instituteFilterDto));
	}

	@RequestMapping(value = "allCategoryType", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllCategoryType() throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<InstituteCategoryType> categoryTypes = instituteService.getAllCategories();
			if (categoryTypes != null && !categoryTypes.isEmpty()) {
				response.put("message", "CategoryType fetched successfully");
				response.put("status", HttpStatus.OK.value());
				response.put("data", categoryTypes);
			} else {
				response.put("message", "CategoryType not found");
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("data", "Error");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.put("message", "Error CategoryType fetched");
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("data", "Error");
		}
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(value = "type", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllInstituteType() throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<InstituteType> instituteTypes = instituteTypeService.getAllInstituteType();
			if (instituteTypes != null && !instituteTypes.isEmpty()) {
				response.put("message", "Institute type fetched successfully");
				response.put("status", HttpStatus.OK.value());
				response.put("data", instituteTypes);
			} else {
				response.put("message", "Institute type not vound");
				response.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.put("message", "Error CategoryType fetched");
			response.put("status", HttpStatus.BAD_REQUEST.value());
		}
		return ResponseEntity.ok().body(response);
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

	@GetMapping(value = "/nearest/pageNumber/{pageNumber}/pageSize/{pageSize}", produces = "application/json")
	public ResponseEntity<?> getNearestInstituteList(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(value = "latitude", required = true) final Double latitude,
			@RequestParam(value = "longitude", required = true) final Double longitude) throws ValidationException {
		List<NearestInstituteDTO> nearestInstituteDTOs = instituteService.getNearestInstituteList(pageNumber, pageSize, latitude, longitude);
		return new GenericResponseHandlers.Builder().setData(nearestInstituteDTOs).setMessage("Nearest Institute Displayed Successfully.")
				.setStatus(HttpStatus.OK).create();
	}
	
	
	@RequestMapping(value = "/instituteNames/distinct/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDistinctInstututes(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String instituteName)
			throws Exception {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		int totalCount = instituteService.getDistinctInstituteCount(instituteName);
		List<InstituteResponseDto> instituteList = instituteService.getDistinctInstituteList(startIndex, pageSize, instituteName);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Institutes List Successfully");
		responseMap.put("data", instituteList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

}
