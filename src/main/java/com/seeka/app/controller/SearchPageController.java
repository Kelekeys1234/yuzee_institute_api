package com.seeka.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Level;
import com.seeka.app.bean.User;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.CourseSearchFilterDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.jobs.CountryLevelFacultyUtil;
import com.seeka.app.service.ICountryService;
import com.seeka.app.service.IFacultyService;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.ILevelService;
import com.seeka.app.service.IUserService;

@RestController
@RequestMapping("/search")
public class SearchPageController {
	
	@Autowired
	ICountryService countryService;
	
	@Autowired
	ILevelService levelService;
	
	@Autowired
	IFacultyService facultyService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IInstituteService instituteService;
	
	
	@RequestMapping(value = "/get/all/{userid}", method=RequestMethod.GET)
	public ResponseEntity<?>  getAllCountries(@PathVariable("userid") UUID userId) {
		Map<String,Object> response = new HashMap<String, Object>();
		Date now = new Date();
		User user = userService.get(userId);
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		List<UUID> countryIds = new ArrayList<>();
		if(null != user.getCountryId()) {
			countryIds.add(user.getCountryId());
			courseSearchDto.setCountryIds(countryIds);
		}
		CourseSearchFilterDto sortingObj = new CourseSearchFilterDto();
		sortingObj.setWorldRanking("DSC");
		courseSearchDto.setSortingObj(sortingObj);
		courseSearchDto.setPageNumber(1);
		courseSearchDto.setMaxSizePerPage(3);
		List<InstituteResponseDto> recommendedInstList = instituteService.getAllInstitutesByFilter(courseSearchDto);
		List<CountryDto> countryList = CountryLevelFacultyUtil.getCountryList();
		List<Level> levelList = CountryLevelFacultyUtil.getLevelList();
		response.put("countryList",countryList);
		response.put("levelList",levelList);
		response.put("recommendedInstList",recommendedInstList);
		response.put("status", 1);
		response.put("message","Success.!");
		long duration  = new Date().getTime() - now.getTime();
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		System.out.println("Total Computation in "+diffInSeconds);
    	return ResponseEntity.accepted().body(response);
	} 
	
	 
	
}
