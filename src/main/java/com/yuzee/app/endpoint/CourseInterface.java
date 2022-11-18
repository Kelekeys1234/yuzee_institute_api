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
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseFilterDto;
import com.yuzee.app.dto.CourseMobileDto;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;

@RestController
@RequestMapping(path = "/api/v1")
public interface CourseInterface {

	@PostMapping("/institute/{instituteId}/course")
	public ResponseEntity<?> save(@RequestHeader("userId") final String userId,
			@PathVariable("instituteId") String instituteId, @Valid @RequestBody final CourseRequest course)
			throws Exception;

	@PutMapping("/institute/{instituteId}/course/{id}")
	public ResponseEntity<?> update(@RequestHeader(required = true) final String userId,
			@PathVariable String instituteId, @Valid @RequestBody final CourseRequest course,
			@PathVariable final String id)
			throws ValidationException, NotFoundException, ForbiddenException, InvokeException, Exception;

	@GetMapping("/course/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllCourse(@PathVariable("pageNumber") final Integer pageNumber,
			@PathVariable("pageSize") final Integer pageSize) throws Exception;

	@GetMapping("/course/autoSearch/{searchKey}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> autoSearch(@PathVariable final String searchKey, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize) throws Exception;

	@DeleteMapping("/course/{id}/{linked_course_ids}")
	public ResponseEntity<?> delete(@RequestHeader(required = true) final String userId,

			@Valid @PathVariable final String id,
			@PathVariable(value = "linked_course_ids") final List<String> linkedCourseIds)
			throws ForbiddenException, NotFoundException;

//yet to implement
	@GetMapping("/course/search/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> searchCourse(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final List<String> countryIds,
			@RequestParam(required = false) final String instituteId,
			@RequestParam(required = false) final List<String> facultyIds,
			@RequestParam(required = false) final List<String> cityIds,
			@RequestParam(required = false) final List<String> levelIds,
			@RequestParam(required = false) final List<String> serviceIds,
			@RequestParam(required = false) final Double minCost, @RequestParam(required = false) final Double maxCost,
			@RequestParam(required = false) final Integer minDuration,
			@RequestParam(required = false) final Integer maxDuration,
			@RequestParam(required = false) final String courseName,
			@RequestParam(required = false) final String currencyCode,
			@RequestParam(required = false) final String searchKeyword,
			@RequestParam(required = false) final String sortBy,
			@RequestParam(required = false) final boolean sortAsscending,
			@RequestHeader(required = true) final String userId, @RequestParam(required = false) final String date)
			throws ValidationException, InvokeException, NotFoundException;

	@PostMapping(value = "/course/search")
	public ResponseEntity<?> searchCourse(@RequestHeader(required = true) final String userId,
			@RequestBody final CourseSearchDto courseSearchDto) throws Exception;

	@PostMapping(value = "/course/advanceSearch")
	public ResponseEntity<?> advanceSearch(@RequestHeader(required = true) final String userId,
			@RequestHeader(required = false) final String language, @RequestBody final AdvanceSearchDto courseSearchDto)
			throws Exception;

	@GetMapping("/course/{id}")
	public ResponseEntity<Object> get(@RequestHeader(required = false) final String userId,
			@Valid @PathVariable final String id,
			@RequestParam(name = "is_readable_id", required = false) boolean isReadableId) throws Exception;

	@PutMapping(value = "/course/institute/{instituteId}")
	public ResponseEntity<?> getAllCourseByInstituteID(@Valid @PathVariable final String instituteId,
			@Valid @RequestBody final CourseSearchDto request) throws Exception;

	@GetMapping(value = "/course/keyword/{keyword}")
	public ResponseEntity<?> searchCourseKeyword(@PathVariable(value = "keyword") final String keyword)
			throws Exception;

///yet to implements
	@GetMapping(value = "/course/faculty/{facultyId}")
	public ResponseEntity<?> getCoursesByFacultyId(@Valid @PathVariable final String facultyId) throws Exception;

	@GetMapping(value = "/course/user/{courseIds}/{sortBy}/{sortAsscending}")
	public ResponseEntity<?> getUserCourses(@PathVariable final List<String> courseIds,
			@PathVariable final String sortBy, @PathVariable final String sortAsscending) throws ValidationException;

	@PostMapping(value = "/course/filter")
	public ResponseEntity<?> courseFilter(@RequestHeader(required = true) final String userId,
			@RequestHeader(required = false) final String language, @RequestBody final CourseFilterDto courseFilter)
			throws Exception;

	@GetMapping(value = "/course/autoSearch/{searchKey}")
	public ResponseEntity<?> autoSearchByCharacter(@PathVariable final String searchKey) throws Exception;

	@GetMapping(value = "/course/noResult/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<Object> getCourseNoResultRecommendation(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String facultyId,
			@RequestParam(required = false) final String countryId,
			@RequestParam(required = false) final String userCountry)
			throws ValidationException, InvokeException, NotFoundException;

	@GetMapping(value = "/course/keyword/recommendatation/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<Object> getCourseKeywordRecommendation(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String facultyId,
			@RequestParam(required = false) final String countryId,
			@RequestParam(required = false) final String levelId) throws ValidationException;

	@GetMapping(value = "/course/cheapest/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<Object> getCheapestCourse(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String facultyId,
			@RequestParam(required = false) final String countryId,
			@RequestParam(required = false) final String levelId, @RequestParam(required = false) final String cityId)
			throws ValidationException;

	@GetMapping(value = "/course/getCourseCountByLevel")
	public ResponseEntity<Object> getCourseCountByLevel();

	@GetMapping(value = "/course/names/distinct/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getDistinctCourses(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String name);

	@PostMapping(value = "/course/mobile/{instituteId}")
	public ResponseEntity<?> addCourseViaMobile(@RequestHeader("userId") final String userId,
			@PathVariable final String instituteId, @Valid @RequestBody CourseMobileDto courseMobileDto)
			throws Exception;

	@PutMapping(value = "/course/mobile/{courseId}")
	public ResponseEntity<?> updateCourseViaMobile(@RequestHeader("userId") final String userId,
			@PathVariable final String courseId, @Valid @RequestBody CourseMobileDto courseMobileDto) throws Exception;

	@GetMapping(value = "/course/mobile/{instituteId}")
	public ResponseEntity<?> getCourseViaMobile(@RequestHeader("userId") final String userId,
			@PathVariable final String instituteId,
			@RequestParam(name = "faculty_id", required = true) final String facultyId,
			@RequestParam(name = "status", required = true) final boolean status) throws Exception;

	@GetMapping(value = "/course/public/mobile/{instituteId}")
	public ResponseEntity<?> getCourseViaMobile(@PathVariable final String instituteId,
			@RequestParam(name = "faculty_id", required = true) final String facultyId) throws Exception;

	@PutMapping(value = "/course/status/{courseId}")
	public ResponseEntity<?> changeStatus(@RequestHeader("userId") final String userId,
			@PathVariable final String courseId, @RequestParam(name = "status", required = false) final boolean status)
			throws Exception;

	@GetMapping(value = "/course/institute/pageNumber/{pageNumber}/pageSize/{pageSize}/{instituteId}")
	public ResponseEntity<?> getCourseByInstituteId(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
			@PathVariable final String instituteId) throws NotFoundException;

///// YET  TO IMPLEMENTS
	@PostMapping(value = "/course/nearest")
	public ResponseEntity<?> getNearestCourseList(@RequestBody final AdvanceSearchDto courseSearchDto) throws Exception;

	@PostMapping(value = "/course/pageNumber/{pageNumber}/pageSize/{pageSize}/{countryName}")
	public ResponseEntity<?> getCourseByCountryName(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
			@PathVariable String countryName) throws NotFoundException;

	@PostMapping("/course/courseIds")
	public ResponseEntity<?> getCourseByIds(@RequestBody List<String> courseIds);

	@GetMapping("/course/recommendation/{courseId}")
	public ResponseEntity<?> getRecommendateCourses(@PathVariable String courseId) throws ValidationException;

	@GetMapping("/course/related/{courseId}")
	public ResponseEntity<?> getRelatedCourses(@PathVariable String courseId) throws ValidationException;

	@GetMapping("/course/count/{instituteId}")
	public ResponseEntity<?> getCourseCountByInstituteId(@PathVariable String instituteId) throws ValidationException;

	// api just according to the form requirements
	@PostMapping("/institute/{instituteId}/course/basic/info")
	ResponseEntity<?> saveBasicCourse(@RequestHeader(value = "userId", required = true) String userId,
			@PathVariable String instituteId, @Valid @RequestBody CourseRequest course)
			throws ValidationException, NotFoundException, ForbiddenException, InvokeException;

	// api just according to the form requirements
	@PutMapping("/institute/{instituteId}/course/basic/info/{id}")
	ResponseEntity<?> updateBasicCourse(@RequestHeader(value = "userId", required = true) String userId,
			@PathVariable String instituteId, @Valid @RequestBody CourseRequest course, @PathVariable String id)
			throws ValidationException, NotFoundException, ForbiddenException, InvokeException;

	@PutMapping("/course/procedure_id")
	public ResponseEntity<?> updateProcedureIdInCourse(
			@RequestParam(name = "course_ids", required = false) List<String> courseIds,
			@RequestParam(name = "student_type", required = false) String studentType,
			@RequestParam(name = "procedure_id", required = false) String procedureId);

	@PutMapping("/course/procedure_id/institute_id/{instituteIds}/{studentType}/{procedureId}")
	public ResponseEntity<?> updateProcedureIdInCourseByInstituteId(@PathVariable("instituteIds") String instituteIds,
			@PathVariable String studentType, @PathVariable String procedureId);

	@PostMapping(value = "/course/draft/publish/{courseId}")
	public ResponseEntity<?> publishDraftCourse(@RequestHeader(value = "userId", required = true) String userId,
			@PathVariable final String courseId);

	@GetMapping(value = "/course/draft/pageNumber/{pageNumber}/pageSize/{pageSize}/{name}/{instituteId}")
	public ResponseEntity<?> getDraftCourses(@RequestHeader(value = "userId") String userId,
			@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize, @PathVariable String name,
			@PathVariable String instituteId);

	@DeleteMapping(value = "/course/draft/{courseId}")
	public ResponseEntity<?> discardDraftCourse(@RequestHeader(value = "userId", required = true) String userId,
			@PathVariable final String courseId);
}