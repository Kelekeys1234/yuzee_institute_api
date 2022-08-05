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
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.InstituteFilterDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.LatLongDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;

@RestController
@RequestMapping(path = "/api/v1")
public interface InstituteInterface {
	// TODO done
	@PutMapping("/institute/status/{instituteId}")
	public ResponseEntity<Object> changeStatus(@RequestHeader("userId") final String userId,
			@PathVariable("instituteId") final String instituteId,
			@RequestParam(name = "status", required = false) final boolean status);

	// TODO done
	@PostMapping("/instituteType")
	public ResponseEntity<?> saveInstituteType(@RequestHeader(value = "instituteId") String instituteId,
			@RequestParam(value = "instituteType", required = false) String instituteType) throws Exception;

	// TODO done
	@GetMapping("/type/{countryName}")
	public ResponseEntity<?> getInstituteTypeByCountry(@PathVariable String countryName) throws Exception;

	// TODO done
	@GetMapping("/institute/type")
	public ResponseEntity<?> getInstituteType() throws Exception;

	// TODO done
	@PostMapping("/search")
	public ResponseEntity<?> instituteSearch(@RequestBody final CourseSearchDto request) throws Exception;

	@GetMapping("/search/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> instituteSearch(@PathVariable("pageNumber") final Integer pageNumber,
			@PathVariable("pageSize") final Integer pageSize,
			@RequestParam(required = false) final List<String> countryNames,
			@RequestParam(required = false) final List<String> facultyIds,
			@RequestParam(required = false) final List<String> levelIds,
			@RequestParam(required = false) final String cityName,
			@RequestParam(required = false) final String instituteType,
			@RequestParam(required = false) final Boolean isActive,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date updatedOn,
			@RequestParam(required = false) final Integer fromWorldRanking,
			@RequestParam(required = false) final Integer toWorldRanking,
			@RequestParam(required = false) final String sortByField,
			@RequestParam(required = false) final String sortByType,
			@RequestParam(required = false) final String searchKeyword,
			@RequestParam(required = false) final Double latitude,
			@RequestParam(required = false) final Double longitude) throws ValidationException;

	@PostMapping("/recommended")
	public ResponseEntity<?> getAllRecommendedInstitutes(@RequestBody final CourseSearchDto request) throws Exception;

	// TODO done
	@GetMapping("/city/{cityName}")
	public ResponseEntity<?> getInstituteByCityName(@Valid @PathVariable final String cityName) throws Exception;

	// TODO done
	@PostMapping()
	public ResponseEntity<?> save(@RequestBody final ValidList<InstituteRequestDto> institutes) throws Exception;

	// TODO done
	@PutMapping("/{instituteId}")
	public ResponseEntity<?> update(@RequestHeader("userId") final String userId,
			@PathVariable final String instituteId, @Valid @RequestBody final InstituteRequestDto institute)
			throws Exception;

	// TODO done
	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllInstitute(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize) throws Exception;

	// TODO done
	@GetMapping("/autoSearch/{searchKey}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllInstitute(@PathVariable final String searchKey,
			@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception;

	// TODO done
	@GetMapping("/{instituteId}")
	public ResponseEntity<?> get(@RequestHeader("userId") final String userId,
			@PathVariable("instituteId") final String instituteId,
			@RequestParam(name = "is_readable_id", required = false) boolean isReadableId) throws Exception;

	// TODO done
	@GetMapping("/search/{searchText}")
	public ResponseEntity<?> searchInstitute(@Valid @PathVariable final String searchText) throws Exception;

	@PostMapping("/filter")
	public ResponseEntity<?> instituteFilter(@RequestBody final InstituteFilterDto instituteFilterDto) throws Exception;

	// TODO there is no Enumeration type of InstituteCategoryType in common-lib.
	@GetMapping("/allCategoryType")
	public ResponseEntity<?> getAllCategoryType() throws Exception;

	// TODO below method repeated
	@GetMapping()
	public ResponseEntity<?> getAllInstituteType() throws Exception;

	@DeleteMapping("/{instituteId}")
	public ResponseEntity<?> delete(@PathVariable final String instituteId) throws ValidationException;

	// TODO Course entity involved in below API
	@GetMapping("/images/{instituteId}")
	public ResponseEntity<?> getInstituteImage(@PathVariable final String instituteId) throws Exception;

	// TODO Course entity involved in below API
	@GetMapping("/totalCourseCount")
	public ResponseEntity<?> getTotalCourseForInstitute(
			@RequestParam(value = "instituteId", required = true) final String instituteId) throws ValidationException;

	@GetMapping("/history/domestic/ranking/{instituteId}")
	public ResponseEntity<?> getHistoryOfDomesticRanking(@PathVariable(value = "instituteId") final String instituteId)
			throws ValidationException;

	@GetMapping("/history/world/ranking/{instituteId}")
	public ResponseEntity<?> getHistoryOfWorldRanking(@PathVariable(value = "instituteId") final String instituteId)
			throws ValidationException;

	// TODO Course entity involved in below API
	@PostMapping("/domesticRankingForCourse")
	public ResponseEntity<?> getDomesticRanking(@RequestBody final List<String> courseIds) throws ValidationException;

	@PostMapping("/nearest")
	public ResponseEntity<?> getNearestInstituteList(@RequestBody final AdvanceSearchDto courseSearchDto)
			throws Exception;

	// TODO below method calls 3 different services
	@GetMapping("/names/distinct/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getDistinctInstitutes(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final String name) throws Exception;

	// TODO Course entity involved in below API
	@PostMapping("/bounded/area/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getBoundedInstituteList(@PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestBody List<LatLongDto> latLongDto) throws ValidationException;

	// TODO Course entity involved in below API
	@GetMapping(value = "/institute/pageNumber/{pageNumber}/pageSize/{pageSize}/{countryName}")
	public ResponseEntity<?> getInstituteByCountryName(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
			@PathVariable String countryName) throws NotFoundException;

	// TODO below API returns Faculty, that is associated with Course Entity
	@GetMapping("/faculty/instituteId/{instituteId}")
	public ResponseEntity<?> getInstituteFaculties(@PathVariable final String instituteId) throws NotFoundException;

	// TODO Course entity involved in below API
	@GetMapping("/course-faculty-scholarship/count/instituteId/{instituteId}")
	public ResponseEntity<?> getInstituteCourseScholarshipAndFacultyCount(@PathVariable final String instituteId)
			throws NotFoundException;

	// TODO done
	@GetMapping("/institute/multiple/id")
	public ResponseEntity<?> getInstitutesByIdList(
			@RequestParam(name = "institute_ids", required = true) List<String> instituteIds)
			throws ValidationException, NotFoundException, InvokeException, Exception;

	// TODO Done
	@GetMapping("/institute/{readableId}/exists")
	public ResponseEntity<?> instituteExistsByReadableId(@PathVariable final String readableId)
			throws ValidationException, NotFoundException, InvokeException, Exception;

	// TODO below API calls many other micro-services.
	@GetMapping("/institute/{instituteId}/verify")
	public ResponseEntity<?> getInstituteVerificationStatus(@PathVariable final String instituteId);

	// TODO below API calls many other micro-services.
	@GetMapping("/institute/verification-status")
	public ResponseEntity<?> getMultipleInstituteVerificationStatus(
			@RequestParam(name = "institute_ids", required = true) List<String> instituteIds);

	// TODO below API calls many other micro-services.
	@PutMapping(path = "/admin/institute/verify_institutes/{verified}")
	public ResponseEntity<Object> verifyInstitutes(@RequestHeader("userId") String userId,
			@RequestParam("verified_institute_ids") List<String> verifiedInstituteIds,
			@PathVariable("verified") final boolean verified);

}
