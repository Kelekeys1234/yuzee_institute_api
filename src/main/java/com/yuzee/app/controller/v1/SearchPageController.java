package com.yuzee.app.controller.v1;

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

import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.CourseSearchFilterDto;
import com.yuzee.app.dto.InstituteResponseDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.enumeration.ImageCategory;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.processor.StorageProcessor;

@RestController("searchPageControllerV1")
@RequestMapping("/api/v1/search")
public class SearchPageController {

	@Autowired
	private InstituteProcessor instituteProcessor;

	@Autowired
	private StorageProcessor storageProcessor;

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
		List<InstituteResponseDto> recommendedInstList = instituteProcessor.getAllInstitutesByFilter(courseSearchDto, null, null, null, null, null, null, null,
				null, null, null);

		for (InstituteResponseDto obj : recommendedInstList) {
			List<StorageDto> storageDTOList = storageProcessor.getStorageInformation(obj.getId(), ImageCategory.INSTITUTE.toString(), null, "en");
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
