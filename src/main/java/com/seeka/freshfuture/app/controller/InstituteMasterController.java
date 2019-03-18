package com.seeka.freshfuture.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.freshfuture.app.bean.CourseType;
import com.seeka.freshfuture.app.bean.Faculty;
import com.seeka.freshfuture.app.bean.Institute;
import com.seeka.freshfuture.app.dto.ErrorDto;
import com.seeka.freshfuture.app.service.ICountryService;
import com.seeka.freshfuture.app.service.ICourseTypeService;
import com.seeka.freshfuture.app.service.IFacultyService;
import com.seeka.freshfuture.app.service.IInstituteService;

@RestController
@RequestMapping("/institute/master")
public class InstituteMasterController {

	@Autowired
	IFacultyService facultyService;
	
	@Autowired
	ICourseTypeService courseTypeService;
	
	@Autowired
	ICountryService countryService;
	
	@Autowired
	IInstituteService instituteService;
	
	
	@RequestMapping(value = "/getcoursetype/{countryid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCourseTypeByCountry(@PathVariable Integer countryid) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
        List<CourseType> courseTypeList = courseTypeService.getCourseTypeByCountryId(countryid);
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("courseTypeList",courseTypeList); 
		return ResponseEntity.accepted().body(response);
		
	}
	
	@RequestMapping(value = "/getfaculty/{countryid}/{coursetypeid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getFacultyeByCountryAndCourseType(@PathVariable Integer countryid,@PathVariable Integer coursetypeid) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Faculty> facultyList = facultyService.getFacultyByCountryIdAndCourseTypeId(countryid, coursetypeid);
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("facultyList",facultyList);
		return ResponseEntity.accepted().body(response);
	}
	
	
	@RequestMapping(value = "/get", method=RequestMethod.GET)
	public ResponseEntity<?>  getAll() {
		Map<String,Object> response = new HashMap<String, Object>();
		List<Institute> list = instituteService.getAll();
		response.put("status", 1);
		response.put("message","Success.!");
    	response.put("list",list);
    	return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@PathVariable Integer id) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
 			
		Institute instituteObj = instituteService.get(id);
		if(null == instituteObj) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Institute Not Found.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.put("status", 1);
		response.put("message","Success");
		response.put("instituteObj", instituteObj);
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/getallinstitutebycountry/{countryId}", method=RequestMethod.GET)
	public ResponseEntity<?>  getAllInstituteByCountry(@PathVariable Integer countryId) throws Exception {	
		Map<String,Object> response = new HashMap<String, Object>();		
		List<Institute> instituteList = instituteService.getAllInstituteByCountry(countryId);		
		response.put("status", 1);
		response.put("message","Success.!");
    	response.put("instituteList",instituteList);
    	return ResponseEntity.accepted().body(response);
	}

}
