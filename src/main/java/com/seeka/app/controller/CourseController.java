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
import com.seeka.app.bean.CourseKeyword;
import com.seeka.app.bean.CoursePricing;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.FacultyLevel;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteDetails;
import com.seeka.app.bean.InstituteLevel;
import com.seeka.app.dto.CourseDto;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.service.ICourseDetailsService;
import com.seeka.app.service.ICourseKeywordService;
import com.seeka.app.service.ICoursePricingService;
import com.seeka.app.service.ICourseService;
import com.seeka.app.service.IFacultyLevelService;
import com.seeka.app.service.IFacultyService;
import com.seeka.app.service.IInstituteDetailsService;
import com.seeka.app.service.IInstituteLevelService;
import com.seeka.app.service.IInstituteService;
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
	ICourseKeywordService courseKeywordService;
	
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
	public ResponseEntity<?> get(@Valid @PathVariable Integer courseid) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		Course courseObj = courseService.get(courseid);
		if(courseObj == null) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid course.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		
		Institute institute = courseObj.getInstituteObj();
		
		InstituteDetails instituteDetails = instituteDetailsService.get(institute.getId());
		
		InstituteResponseDto instituteObj = new InstituteResponseDto();
		instituteObj.setInstituteId(institute.getId());
		instituteObj.setInstituteImageUrl("https://www.adelaide.edu.au/front/images/mo-orientation.jpg");
		instituteObj.setInstituteLogoUrl("https://global.adelaide.edu.au/v/style-guide2/assets/img/logo.png");
		instituteObj.setInstituteName(institute.getName());
		instituteObj.setLocation(institute.getCityObj().getName()+", "+institute.getCountryObj().getName());
		instituteObj.setStars(courseObj.getStars());
		instituteObj.setWorldRanking(String.valueOf(institute.getWorldRanking()));
		instituteObj.setAboutUs(instituteDetails.getAboutUsInfo());
		instituteObj.setClosingHour(instituteDetails.getClosingHour());
		instituteObj.setInterEmail(institute.getInterEmail());
		instituteObj.setInterPhoneNumber(institute.getInterPhoneNumber());
		instituteObj.setLatitute(institute.getLatitude());
		instituteObj.setLongitude(institute.getLongitude());
		instituteObj.setOpeningHour(instituteDetails.getOpeningHour());
		instituteObj.setTotalNoOfStudents(institute.getTotalNoOfStudent());
		instituteObj.setWebsite(institute.getWebsite());
		instituteObj.setAddress(institute.getAddress());
		instituteObj.setVisaRequirement(institute.getCountryObj().getVisa());
		
		CourseDto courseResObj = new CourseDto();
		
		CoursePricing coursePricing = coursePricingService.getPricingByCourseId(courseObj.getId());
		
		courseResObj.setCourseName(courseObj.getName());
		courseResObj.setCost(coursePricing.getCostRange()+" "+coursePricing.getCurrency());
		courseResObj.setCostOfLiving("");
		courseResObj.setCourseId(courseObj.getId());
		courseResObj.setCourseLanguage(courseObj.getCourseLanguage());
		courseResObj.setDescription(courseObj.getDescription());
		courseResObj.setDuration(courseObj.getDuration());
		courseResObj.setDurationTime(courseObj.getDurationTime());
		courseResObj.setFacultyName(courseObj.getFacultyObj().getName());
		courseResObj.setIntlFees(coursePricing.getInternationFees()+" "+coursePricing.getCurrency());
		courseResObj.setLanguageShortKey(courseObj.getCourseLanguage());
		courseResObj.setLevelName(courseObj.getFacultyObj().getLevelObj().getName());
		courseResObj.setLocalFees(coursePricing.getLocalFees()+" "+coursePricing.getCurrency());
		courseResObj.setStars(courseObj.getStars());
		courseResObj.setWorldRanking(courseObj.getWorldRanking());
	 
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("courseObj",courseResObj);
		response.put("instituteObj",instituteObj);
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/get/all/{instituteid}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> getAllCourseByInstituteID(@Valid @PathVariable Integer instituteid, @Valid @RequestBody CourseSearchDto request ) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		InstituteResponseDto instituteResponseDto = instituteService.getInstituteByID(instituteid);
		if(null == instituteResponseDto) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid institute selection.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		 
		List<CourseResponseDto> courseList = courseService.getAllCoursesByInstitute(instituteid, request);
		
		Integer maxCount = 0,totalCount =0;
		if(null != courseList && !courseList.isEmpty()) {
			totalCount = courseList.get(0).getTotalCount();
			maxCount = courseList.size();
		}
		boolean showMore;
		if(request.getMaxSizePerPage() == maxCount) {
			showMore = true;
		} else {
			showMore = false;
		} 
		response.put("status", 1);
		response.put("message","Success.!");
		response.put("paginationObj",new PaginationDto(totalCount,showMore));
		response.put("instituteObj",instituteResponseDto);
		response.put("courseList",courseList);
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
		List<CourseKeyword> searchkeywordList  = courseKeywordService.searchCourseKeyword(keyword);		
		response.put("status", 1);
		response.put("searchkeywordList", searchkeywordList);
		response.put("message","Success");		
		return ResponseEntity.accepted().body(response);
	}
	
}
         