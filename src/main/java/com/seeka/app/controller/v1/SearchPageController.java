package com.seeka.app.controller.v1;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.CourseSearchFilterDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.IStorageService;

@RestController("searchPageControllerV1")
@RequestMapping("/api/v1/search")
public class SearchPageController {

	@Autowired
	private IInstituteService instituteService;

	@Autowired
	private IStorageService iStorageService;

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCountries(@PathVariable("userId") final String userId) throws ValidationException {
		Map<String, Object> response = new HashMap<>();
		Date now = new Date();
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		CourseSearchFilterDto sortingObj = new CourseSearchFilterDto();
		sortingObj.setWorldRanking("DSC");
		courseSearchDto.setSortingObj(sortingObj);
		courseSearchDto.setPageNumber(1);
		courseSearchDto.setMaxSizePerPage(3);
		List<InstituteResponseDto> recommendedInstList = instituteService.getAllInstitutesByFilter(courseSearchDto, null, null, null, null, null, null, null,
				null, null, null);

		for (InstituteResponseDto obj : recommendedInstList) {
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(obj.getId(), ImageCategory.INSTITUTE.toString(), null, "en");
			obj.setStorageList(storageDTOList);
		}
		//List<CountryDto> countryList = CountryLevelFacultyUtil.getCountryList();
		//List<Level> levelList = CountryLevelFacultyUtil.getLevelList();
		response.put("countryList", null);
		response.put("levelList", null);
		response.put("recommendedInstList", recommendedInstList);
		response.put("status", HttpStatus.OK.value());
		response.put("message", "Success.!");
		long duration = new Date().getTime() - now.getTime();
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		System.out.println("Total Computation in " + diffInSeconds);
		return ResponseEntity.accepted().body(response);
	}

}
