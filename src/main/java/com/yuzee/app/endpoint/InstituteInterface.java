package com.yuzee.app.endpoint;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.InstituteFilterDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.InstituteTypeDto;
import com.yuzee.app.dto.LatLongDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping(path = "/api/v1")
public interface InstituteInterface {

	@PostMapping("/instituteType")
	public ResponseEntity<?> saveInstituteType(@Valid @RequestBody final InstituteTypeDto instituteTypeDto) throws Exception;
	
	@GetMapping("/type")
	public ResponseEntity<?> getInstituteTypeByCountry(@RequestParam String countryName) throws Exception;
	
	@GetMapping("/{instituteId}/service")
	public ResponseEntity<?> getAllServicesByInstitute(@Valid @PathVariable final String instituteId) throws Exception;
	
	@PostMapping("/search")
	public ResponseEntity<?> instituteSearch(@RequestBody final CourseSearchDto request) throws Exception;
	
	@GetMapping("/search/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> instituteSearch(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final List<String> countryNames, @RequestParam(required = false) final List<String> facultyIds,
			@RequestParam(required = false) final List<String> levelIds, @RequestParam(required = false) final String cityName,
			@RequestParam(required = false) final String instituteType, @RequestParam(required = false) final Boolean isActive,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date updatedOn,
			@RequestParam(required = false) final Integer fromWorldRanking, @RequestParam(required = false) final Integer toWorldRanking,
			@RequestParam(required = false) final String sortByField, @RequestParam(required = false) final String sortByType,
			@RequestParam(required = false) final String searchKeyword,
			@RequestParam(required = false) final Double latitutde, @RequestParam(required = false) final Double longitude) throws ValidationException;
	
	@PostMapping("/recommended")
	public ResponseEntity<?> getAllRecommendedInstitutes(@RequestBody final CourseSearchDto request) throws Exception;
	
	@GetMapping("/city/{cityName}")
	public ResponseEntity<?> getInstituteByCityName(@Valid @PathVariable final String cityName) throws Exception;
	
	@GetMapping("/multiple/city/{cityName}")
	public ResponseEntity<?> getInstituteByListOfCityName(@Valid @PathVariable final String cityName) throws Exception;
	
	@PostMapping()
	public ResponseEntity<?> save(@RequestHeader("userId") final String userId, @Valid @RequestBody final List<InstituteRequestDto> institutes) throws Exception;
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestHeader("userId") final String userId, @Valid @PathVariable final String id, @RequestBody final List<InstituteRequestDto> institute) throws Exception;
	
	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllInstitute(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception;
	
	@GetMapping("/autoSearch/{searchKey}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllInstitute(@PathVariable final String searchKey, @PathVariable final Integer pageNumber, 
				@PathVariable final Integer pageSize) throws Exception;
	
	@GetMapping("/{instituteId}")
	public ResponseEntity<?> get(@PathVariable final String instituteId)
			throws ValidationException, NotFoundException, InvokeException, Exception;

	@GetMapping("/search/{searchText}")
	public ResponseEntity<?> searchInstitute(@Valid @PathVariable final String searchText) throws Exception;
	
	@PostMapping("/filter")
	public ResponseEntity<?> instituteFilter(@RequestBody final InstituteFilterDto instituteFilterDto) throws Exception;
	
	@GetMapping("/allCategoryType")
	public ResponseEntity<?> getAllCategoryType() throws Exception;
	
	@GetMapping()
	public ResponseEntity<?> getAllInstituteType() throws Exception;
	
	@DeleteMapping("/{instituteId}")
	public ResponseEntity<?> delete(@PathVariable final String instituteId) throws ValidationException;
	
	@GetMapping("/images/{instituteId}")
	public ResponseEntity<?> getInstituteImage(@PathVariable final String instituteId) throws Exception;
	
	@GetMapping("/totalCourseCount")
	public ResponseEntity<?> getTotalCourseForInstitute(@RequestParam(value = "instituteId", required = true) final String instituteId)
			throws ValidationException;
	
	@GetMapping("/history/domestic/ranking")
	public ResponseEntity<?> getHistoryOfDomesticRanking(@RequestParam(value = "instituteId", required = true) final String instituteId)
			throws ValidationException;
	
	@GetMapping("/history/world/ranking")
	public ResponseEntity<?> getHistoryOfWorldRanking(@RequestParam(value = "instituteId", required = true) final String instituteId)
			throws ValidationException;
	
	@PostMapping("/domesticRankingForCourse")
	public ResponseEntity<?> getDomesticRanking(@RequestBody final List<String> courseIds) throws ValidationException;
	
	@PostMapping("/nearest")
	public ResponseEntity<?> getNearestInstituteList(@RequestBody final AdvanceSearchDto courseSearchDto) throws Exception;
	
	@GetMapping("/names/distinct/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getDistinctInstututes(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String name)
			throws Exception;
	
	@PostMapping("/bounded/area/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getBoundedInstituteList(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize, 
			@RequestBody List<LatLongDto> latLongDto) throws ValidationException;
	
	@GetMapping(value = "/institute/pageNumber/{pageNumber}/pageSize/{pageSize}/{countryName}")
	public ResponseEntity<?> getInstituteByCountryName(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
			@PathVariable String countryName) throws NotFoundException;
	
	@GetMapping("/campus/instituteId/{instituteId}")
	public ResponseEntity<?> getInstituteCampuses(@PathVariable final String instituteId) throws NotFoundException;

	@GetMapping("/faculty/instituteId/{instituteId}")
	public ResponseEntity<?> getInstituteFaculties(@PathVariable final String instituteId) throws NotFoundException;
}
