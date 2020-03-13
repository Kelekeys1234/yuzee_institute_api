package com.seeka.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.AccreditedInstituteDetail;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IAccreditedInstituteDetailService;

@RestController("accreditedInstituteDetailControllerV1")
@RequestMapping("/api/v1/accredited/detail")
public class AccreditedInstituteDetailController {

	@Autowired
	private IAccreditedInstituteDetailService iAccreditedInstituteDetailService;

	@PostMapping
	public ResponseEntity<?> addAccreditedInstituteDetail(@RequestBody final AccreditedInstituteDetail accreditedInstituteDetail) throws ValidationException {
		AccreditedInstituteDetail accreditedInstitute2 = iAccreditedInstituteDetailService.addAccreditedInstituteDetail(accreditedInstituteDetail);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(accreditedInstitute2)
				.setMessage("Created accredited Institute details successfully").create();
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAccreditedInstituteDetailList(@RequestParam(name = "entityId") final String entityId,
			@RequestParam(name = "entityType") final String entityType, @PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) {
		List<AccreditedInstituteDetail> accreditedInstituteDetailList = iAccreditedInstituteDetailService.getAccreditedInstituteDetailList(entityId, entityType,
				pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(accreditedInstituteDetailList)
				.setMessage("Get accredited Institute details list successfully").create();
	}

	@GetMapping("{accreditedInstituteId}")
	public ResponseEntity<?> getAccreditedInstituteDetail(@PathVariable final String accreditedInstituteId) {
		List<AccreditedInstituteDetail> accreditedInstituteDetail = iAccreditedInstituteDetailService.getAccreditedInstituteDetail(accreditedInstituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(accreditedInstituteDetail)
				.setMessage("Get accredited Institute details successfully").create();
	}
	
	@GetMapping("{instituteId}")
	public ResponseEntity<?> getAccreditedInstituteDetailsByInstituteId(@PathVariable final String instituteId) {
		List<AccreditedInstituteDetail> accreditedInstituteDetail = iAccreditedInstituteDetailService.getAccreditedInstituteDetail(instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(accreditedInstituteDetail)
				.setMessage("Get accredited Institute details successfully").create();
	}

}
