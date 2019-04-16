package com.seeka.app.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteDetails;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.ServiceDetails;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.service.IInstituteDetailsService;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.IInstituteServiceDetailsService;
import com.seeka.app.service.IInstituteTypeService;
import com.seeka.app.service.IServiceDetailsService;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/institute")
public class InstituteController {

	@Autowired
	IInstituteService instituteService;
	
	@Autowired
	IInstituteDetailsService instituteDetailsService;
	
	@Autowired
	IInstituteTypeService instituteTypeService;
	
	@Autowired
	IServiceDetailsService serviceDetailsService;
	
	@Autowired
	IInstituteServiceDetailsService instituteServiceDetailsService;
	
	@RequestMapping(value = "/type/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> saveInstituteType(@Valid @RequestBody InstituteType instituteTypeObj) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		instituteTypeService.save(instituteTypeObj);
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("instituteTypeObj",instituteTypeObj);
		return ResponseEntity.accepted().body(response);
	}
	 
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> save(@Valid @RequestBody Institute instituteObj) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		instituteObj.setCreatedOn(new Date());
		instituteService.save(instituteObj);
		if(null != instituteObj.getInstituteDetailsObj()) {
			InstituteDetails instituteDetails = instituteObj.getInstituteDetailsObj();
			instituteDetails.setInstituteId(instituteObj.getId());
			instituteDetailsService.save(instituteDetails);
		}
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("instituteObj",instituteObj);
		return ResponseEntity.accepted().body(response);
	}
	
	
	@RequestMapping(value = "/get/{institueid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get(@Valid @PathVariable Integer institueid) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Institute instituteObj = instituteService.get(institueid);
		if(instituteObj == null) {
			ErrorDto errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid institue.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		InstituteDetails instituteDetailsObj = instituteDetailsService.get(institueid);
		if(null != instituteDetailsObj) {
			instituteObj.setInstituteDetailsObj(instituteDetailsObj);
		}
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("instituteObj",instituteObj);
		return ResponseEntity.accepted().body(response);
	}
	
	
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> search(@Valid @RequestParam("searchkey") String searchkey) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<InstituteSearchResultDto> instituteList = instituteService.getInstitueBySearchKey(searchkey);
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("instituteList",instituteList);
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/service/save", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> saveService() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<ServiceDetails> list = new ArrayList<>(); 
		String createdBy = "AUTO";
		Date createdOn = new Date();
		
		ServiceDetails serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Visa Work Benefits");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Visa Work Benefits");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Employment and career development");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Employment and career development");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Counselling – personal and academic");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Counselling – personal and academic");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Study Library Support");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Study Library Support");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Health services");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Health services");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Disability Support");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Disability Support");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Childcare Centre");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Childcare Centre");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Cultural inclusion/anti-racism programs");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Cultural inclusion/anti-racism programs");
		list.add(serviceObj);
		
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Technology Services");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Technology Services");
		list.add(serviceObj);
		
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Accommodation");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Accommodation");
		list.add(serviceObj);
		
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Medical");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Medical");
		list.add(serviceObj);
		
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Legal Services");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Legal Services");
		list.add(serviceObj);
		
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Accounting Services");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Accounting Services");
		list.add(serviceObj);
		
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Bus");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Bus");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Train");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Train");
		list.add(serviceObj);
		
		 
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Airport Pickup");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Airport Pickup");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Swimming pool");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Swimming pool");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Sports Center");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Sports Center");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Sport Teams");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Sport Teams");
		list.add(serviceObj);
		
		serviceObj = new ServiceDetails();
		serviceObj.setCreatedBy(createdBy);
		serviceObj.setCreatedOn(createdOn);
		serviceObj.setDescription("Housing Services");
		serviceObj.setInstituteTypeId(1);
		serviceObj.setIsActive(true);
		serviceObj.setIsDeleted(false);
		serviceObj.setName("Housing Services");
		list.add(serviceObj);
		
		for (ServiceDetails serviceDetails : list) {
			try {
				serviceDetailsService.save(serviceDetails);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("serviceList",list);
		return ResponseEntity.accepted().body(response);
	}
	 
	
	@RequestMapping(value = "/service/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllInstituteService() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<ServiceDetails> list = serviceDetailsService.getAll();;
		
		
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("serviceList",list);
		return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/service/get/{institueid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllServicesByInstitute(@Valid @PathVariable Integer institueid) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> serviceNames = instituteServiceDetailsService.getAllServices(institueid);
        response.put("status", 1);
		response.put("message","Success.!");
		response.put("serviceList",serviceNames);
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
		
		List<InstituteResponseDto> courseList = instituteService.getAllInstitutesByFilter(courseSearchDto);
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
		response.put("instituteList",courseList);
		return ResponseEntity.accepted().body(response);
	}
}
         