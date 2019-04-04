package com.seeka.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.CourseDetails;
import com.seeka.app.bean.CoursePricing;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.FacultyLevel;
import com.seeka.app.bean.InstituteDetails;
import com.seeka.app.bean.InstituteLevel;
import com.seeka.app.bean.SearchKeywords;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.service.ICourseDetailsService;
import com.seeka.app.service.ICoursePricingService;
import com.seeka.app.service.ICourseService;
import com.seeka.app.service.IFacultyLevelService;
import com.seeka.app.service.IFacultyService;
import com.seeka.app.service.IInstituteDetailsService;
import com.seeka.app.service.IInstituteLevelService;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.ISearchKeywordsService;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	IInstituteService instituteService;
	
	@Autowired
	IInstituteDetailsService instituteDetailsService;
	
	@Autowired
	ICourseService courseService;
	
	@Autowired
	ICourseDetailsService courseDetailsService;
	
	@Autowired
	ICoursePricingService coursePricingService;
	
	@Autowired
	ISearchKeywordsService searchKeywordsService;
	
	@Autowired
	IFacultyService facultyService;
	
	@Autowired
	IInstituteLevelService instituteLevelService;
	
	@Autowired
	IFacultyLevelService facultyLevelService;
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> save(@Valid @RequestBody CourseDetails courseDetailsObj) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		if(null == courseDetailsObj.getCourseObj()) {
			ErrorDto errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid course.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		Course course = courseDetailsObj.getCourseObj();
		courseService.save(course);
		
		courseDetailsObj.setCourseId(course.getId());
		courseDetailsService.save(courseDetailsObj);
		
		Faculty faculty = facultyService.get(course.getFacultyObj().getId());
		
		InstituteLevel instituteLevel = new InstituteLevel();
		instituteLevel.setCityId(course.getCityObj().getId());
		instituteLevel.setCountryObj(course.getCountryObj());
		instituteLevel.setInstituteId(course.getInstituteObj().getId());
		instituteLevel.setIsActive(true);
		instituteLevel.setLevelObj(faculty.getLevelObj());
		instituteLevelService.save(instituteLevel);
		
		FacultyLevel facultyLevel = new FacultyLevel();
		facultyLevel.setFacultyId(faculty.getId());
		facultyLevel.setInstituteObj(course.getInstituteObj());
		facultyLevel.setIsActive(true);
		facultyLevelService.save(facultyLevel);
		
		response.put("status", 1);
		response.put("message","Success.!");
		response.put("courseDetailsObj",courseDetailsObj);
		return ResponseEntity.accepted().body(response);
	} 
	
	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getCourseTypeByCountry(@RequestBody CourseSearchDto courseSearchDto ) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(courseSearchDto.getPageNumber() > PaginationUtil.courseResultPageMaxSize) {
			ErrorDto errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Maximum course limit per is "+PaginationUtil.courseResultPageMaxSize);
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		
		List<CourseResponseDto> courseList = courseService.getAllCoursesByFilter(courseSearchDto);
		Integer maxCount = 0,totalCount =0;
		if(null != courseList && !courseList.isEmpty()) {
			totalCount = courseList.get(0).getTotalCount();
			maxCount = courseList.size();
		}
		boolean showMore;
		if(courseSearchDto.getMaxSizePerPage() == maxCount) {
			showMore = true;
		} else {
			showMore = false;
		}
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("paginationObj",new PaginationDto(totalCount,showMore));
		response.put("courseList",courseList);
		return ResponseEntity.accepted().body(response);
	}
	 
	@RequestMapping(value = "/searchbycoursename", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> search(@Valid @RequestParam("searchkey") String searchkey) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setSearchKey(searchkey);
		List<CourseResponseDto> courseList = courseService.getAllCoursesByFilter(courseSearchDto);
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("courseList",courseList);
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/get/{courseid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@Valid @PathVariable Integer courseid ) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Course courseObj = courseService.get(courseid);
		
		if(courseObj == null) {
			ErrorDto errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid course.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		
		InstituteDetails instituteDetailsObj = instituteDetailsService.get(courseObj.getInstituteObj().getId());
		if(null != instituteDetailsObj) {
			courseObj.getInstituteObj().setInstituteDetailsObj(instituteDetailsObj);
		}
		
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("courseObj",courseObj);
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/pricing/save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> saveService(@RequestBody CoursePricing obj) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		coursePricingService.save(obj);		
		response.put("status", 1);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/search/coursekeyword", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchCourseKeyword(@RequestParam(value = "keyword") String keyword) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<SearchKeywords> searchkeywordList  = searchKeywordsService.searchCourseKeyword(keyword);		
		response.put("status", 1);
		response.put("searchkeywordList", searchkeywordList);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
}
         