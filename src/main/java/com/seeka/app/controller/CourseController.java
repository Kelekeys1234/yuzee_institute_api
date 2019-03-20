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
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.InstituteDetails;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.service.ICourseService;
import com.seeka.app.service.IInstituteDetailsService;
import com.seeka.app.service.IInstituteService;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	IInstituteService instituteService;
	
	@Autowired
	IInstituteDetailsService instituteDetailsService;
	
	@Autowired
	ICourseService courseService;
	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getCourseTypeByCountry(@RequestBody CourseSearchDto courseSearchDto ) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Course> courseList = courseService.getAllCoursesByFilter(courseSearchDto);
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
	
	
	
}
         