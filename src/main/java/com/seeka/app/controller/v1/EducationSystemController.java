package com.seeka.app.controller.v1;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.EducationSystemDto;
import com.seeka.app.dto.EducationSystemRequest;
import com.seeka.app.dto.EducationSystemResponse;
import com.seeka.app.service.IEducationSystemService;

@RestController("educationSystemControllerV1")
@RequestMapping("/v1/educationSystem")
public class EducationSystemController {

	@Autowired
	private IEducationSystemService educationSystemService;

	@RequestMapping(value = "/{countryId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getEducationSystems(@PathVariable final String countryId) throws Exception {
		List<EducationSystem> educationSystemList = educationSystemService.getEducationSystemsByCountryId(countryId);
		return new GenericResponseHandlers.Builder().setData(educationSystemList).setMessage("Fetch Education system list successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> saveEducationSystems(@Valid @RequestBody final EducationSystemDto educationSystem) throws Exception {
		return educationSystemService.saveEducationSystems(educationSystem);
	}

	@RequestMapping(value = "/details", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> saveUserEducationDetails(@RequestBody final EducationSystemRequest educationSystemDetails) throws Exception {
		educationSystemService.saveUserEducationDetails(educationSystemDetails);
		return new GenericResponseHandlers.Builder().setMessage("Create User Education system successfully").setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getEducationSystemsDetailByUserId(@PathVariable final String userId) throws Exception {
		EducationSystemResponse educationSystemResponse = educationSystemService.getEducationSystemsDetailByUserId(userId);
		return new GenericResponseHandlers.Builder().setData(educationSystemResponse).setMessage("Get user education system details successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> delete(@Valid @PathVariable final String userId) throws Exception {
		educationSystemService.deleteEducationSystemDetailByUserId(userId);
		return new GenericResponseHandlers.Builder().setMessage("Delete user education system details successfully").setStatus(HttpStatus.OK).create();
	}

	@RequestMapping(value = "/grades/{countryId}/{systemId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getGrades(@PathVariable final BigInteger countryId, @PathVariable final BigInteger systemId) throws Exception {
		return educationSystemService.getGrades(countryId, systemId);
	}

}
