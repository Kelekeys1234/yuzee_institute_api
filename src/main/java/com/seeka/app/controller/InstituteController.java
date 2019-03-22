package com.seeka.app.controller;

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
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.service.IInstituteDetailsService;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.IInstituteTypeService;

@RestController
@RequestMapping("/institue")
public class InstituteController {

	@Autowired
	IInstituteService instituteService;
	
	@Autowired
	IInstituteDetailsService instituteDetailsService;
	
	@Autowired
	IInstituteTypeService instituteTypeService;
	
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
}
         