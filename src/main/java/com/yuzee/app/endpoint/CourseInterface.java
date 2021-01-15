package com.yuzee.app.endpoint;

import java.util.List;

import javax.validation.Valid;

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
import com.yuzee.app.dto.CourseFilterDto;
import com.yuzee.app.dto.CourseMinRequirementDto;
import com.yuzee.app.dto.CourseMobileDto;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping(path = "/api/v1/course")
public interface CourseInterface {

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody final CourseRequest course)
			throws ValidationException, CommonInvokeException, NotFoundException;

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody final CourseRequest course, @PathVariable final String id)
			throws ValidationException, CommonInvokeException, NotFoundException;

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllCourse(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception;

	@GetMapping("/autoSearch/{searchKey}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> autoSearch(@PathVariable final String searchKey, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize) throws Exception;

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@Valid @PathVariable final String id) throws Exception;

	@GetMapping("/search/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> searchCourse(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final List<String> countryIds, @RequestParam(required = false) final String instituteId,
			@RequestParam(required = false) final List<String> facultyIds, @RequestParam(required = false) final List<String> cityIds,
			@RequestParam(required = false) final List<String> levelIds, @RequestParam(required = false) final List<String> serviceIds,
			@RequestParam(required = false) final Double minCost, @RequestParam(required = false) final Double maxCost,
			@RequestParam(required = false) final Integer minDuration, @RequestParam(required = false) final Integer maxDuration, 
			@RequestParam(required = false) final String courseName, @RequestParam(required = false) final String currencyCode,
			@RequestParam(required = false) final String searchKeyword, @RequestParam(required = false) final String sortBy, 
			@RequestParam(required = false) final boolean sortAsscending, @RequestHeader(required = true) final String userId,
			@RequestParam(required = false) final String date) throws ValidationException, InvokeException, NotFoundException;

	@PostMapping(value = "/search")
	public ResponseEntity<?> searchCourse(@RequestHeader(required = true) final String userId,
			@RequestBody final CourseSearchDto courseSearchDto) throws Exception;

	@PostMapping(value = "/advanceSearch")
	public ResponseEntity<?> advanceSearch(@RequestHeader(required = true) final String userId,
			@RequestHeader(required = false) final String language, @RequestBody final AdvanceSearchDto courseSearchDto) throws Exception;

	@GetMapping("/{id}")
	public ResponseEntity<Object> get(@RequestHeader(required = false) final String userId,
			@Valid @PathVariable final String id) throws Exception;

	@PutMapping(value = "/institute/{instituteId}")
	public ResponseEntity<?> getAllCourseByInstituteID(@Valid @PathVariable final String instituteId,
			@Valid @RequestBody final CourseSearchDto request) throws Exception;

	@GetMapping(value = "/keyword")
	public ResponseEntity<?> searchCourseKeyword(@RequestParam(value = "keyword") final String keyword) throws Exception;

	@GetMapping(value = "/faculty/{facultyId}")
	public ResponseEntity<?> getCoursesByFacultyId(@Valid @PathVariable final String facultyId) throws Exception;

	@PostMapping(value = "/user")
	public ResponseEntity<?> getUserCourses(@RequestBody final List<String> courseIds,
			@RequestParam(required = false) final String sortBy, @RequestParam(required = false) final String sortAsscending) 
			throws ValidationException, CommonInvokeException;

	@PostMapping(value = "/filter")
	public ResponseEntity<?> courseFilter(@RequestHeader(required = true) final String userId,
			@RequestHeader(required = false) final String language, @RequestBody final CourseFilterDto courseFilter) throws Exception;

	@PostMapping(value = "/minimumRequirement")
	public ResponseEntity<?> saveCourseMinRequirement(
			@Valid @RequestBody final List<CourseMinRequirementDto> courseMinRequirementDtoList) throws Exception;

	@GetMapping(value = "/minimumRequirement/{courseId}")
	public ResponseEntity<?> getCourseMinRequirement(@PathVariable final String courseId) throws Exception;

	@GetMapping(value = "/autoSearch/{searchKey}")
	public ResponseEntity<?> autoSearchByCharacter(@PathVariable final String searchKey) throws Exception;

	@GetMapping(value = "/noResult/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<Object> getCourseNoResultRecommendation(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = true) final String facultyId,
			@RequestParam(required = true) final String countryId,
			@RequestParam(required = true) final String userCountry) throws ValidationException, InvokeException, NotFoundException;

	@GetMapping(value = "/keyword/recommendatation/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<Object> getCourseKeywordRecommendation(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = true) final String facultyId,
			@RequestParam(required = true) final String countryId, @RequestParam(required = true) final String levelId) throws ValidationException;

	@GetMapping(value = "/cheapest/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<Object> getCheapestCourse(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = true) final String facultyId,
			@RequestParam(required = true) final String countryId, @RequestParam(required = true) final String levelId,
			@RequestParam(required = true) final String cityId) throws ValidationException;

	@GetMapping(value = "/getCourseCountByLevel")
	public ResponseEntity<Object> getCourseCountByLevel();

	@GetMapping(value = "/names/distinct/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getDistinctCourses(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String name);

	@PostMapping(value = "/mobile/{instituteId}")
	public ResponseEntity<?> addCourseViaMobile(@RequestHeader("userId") final String userId,
			@PathVariable final String instituteId, @Valid @RequestBody CourseMobileDto courseMobileDto) throws Exception;

	@PutMapping(value = "/mobile/{courseId}")
	public ResponseEntity<?> updateCourseViaMobile(@RequestHeader("userId") final String userId,
			@PathVariable final String courseId, @Valid @RequestBody CourseMobileDto courseMobileDto) throws Exception;

	@GetMapping(value = "/mobile/{instituteId}")
	public ResponseEntity<?> getCourseViaMobile(@RequestHeader("userId") final String userId,
			@PathVariable final String instituteId,
			@RequestParam(name = "faculty_id", required = true) final String facultyId,
			@RequestParam(name = "status", required = true) final boolean status) throws Exception;

	@GetMapping(value = "/public/mobile/{instituteId}")
	public ResponseEntity<?> getCourseViaMobile(@PathVariable final String instituteId,
			@RequestParam(name = "faculty_id", required = true) final String facultyId) throws Exception;

	@PutMapping(value = "/mobile/change/status/{courseId}")
	public ResponseEntity<?> changeStatus(@RequestHeader("userId") final String userId,
			@PathVariable final String courseId, @RequestParam(name = "status", required = true) final boolean status) throws Exception;

	@GetMapping(value = "/institute/pageNumber/{pageNumber}/pageSize/{pageSize}/{instituteId}")
	public ResponseEntity<?> getCourseByInstituteId(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
			@PathVariable final String instituteId) throws NotFoundException;

	@PostMapping(value = "/nearest", produces = "application/json")
	public ResponseEntity<?> getNearestCourseList(@RequestBody final AdvanceSearchDto courseSearchDto) throws Exception;

	@GetMapping(value = "/course/pageNumber/{pageNumber}/pageSize/{pageSize}/{countryName}")
	public ResponseEntity<?> getCourseByCountryName(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
			@PathVariable String countryName) throws NotFoundException;
	
	@PostMapping("/courseIds")
	public ResponseEntity<?> getCourseByIds(@RequestBody List<String> courseIds);
	
	@GetMapping("/recommendation/{courseId}")
	public ResponseEntity<?> getRecommendateCourses(@PathVariable String courseId) throws ValidationException;
	
	@GetMapping("/related/{courseId}")
	public ResponseEntity<?> getRelatedCourses(@PathVariable String courseId) throws ValidationException;
	
	@GetMapping("/count/{instituteId}")
	public ResponseEntity<?> getCourseCountByInstituteId(@PathVariable String instituteId) throws ValidationException;
	
	@Deprecated
	@GetMapping(value = "/eligibility/update")
	public ResponseEntity<?> updateGradeAndEnglishEligibility() throws Exception;

}
