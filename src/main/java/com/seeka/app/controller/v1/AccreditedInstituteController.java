package com.seeka.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.AccreditedInstitute;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.AccreditedInstituteDto;
import com.seeka.app.dto.AccreditedInstituteRequestDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IAccreditedInstituteService;

@RestController("accreditedInstituteControllerV1")
@RequestMapping("/v1/accredited/institute")
public class AccreditedInstituteController {

	@Autowired
	private IAccreditedInstituteService iAccreditedInstitute;

	@PostMapping
	public ResponseEntity<?> addAccreditedInstitute(@RequestParam(name = "file", required = false) final MultipartFile file,
			@ModelAttribute final AccreditedInstituteRequestDto accreditedInstituteRequestDto) throws ValidationException {
		AccreditedInstitute accreditedInstitute2 = iAccreditedInstitute.addAccreditedInstitute(accreditedInstituteRequestDto);
		if (file != null) {
			iAccreditedInstitute.addAccreditedLogo(file, accreditedInstitute2);
		}
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(accreditedInstitute2)
				.setMessage("Created accredited institute successfully").create();
	}

	@PutMapping("/{accreditedInstituteId}")
	public ResponseEntity<?> updateAccreditedInstitute(@PathVariable final String accreditedInstituteId,
			@RequestParam(name = "file", required = false) final MultipartFile file,
			@ModelAttribute final AccreditedInstituteRequestDto accreditedInstituteRequestDto) throws ValidationException {
		AccreditedInstitute resultAccreditedInstitute = iAccreditedInstitute.updateAccreditedInstitute(accreditedInstituteId, accreditedInstituteRequestDto);
		if (file != null) {
			iAccreditedInstitute.addAccreditedLogo(file, resultAccreditedInstitute);
		}
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(resultAccreditedInstitute)
				.setMessage("Updated accredited institute successfully").create();
	}

	@DeleteMapping("/{accreditedInstituteId}")
	public ResponseEntity<?> deleteAccreditedInstitute(@PathVariable final String accreditedInstituteId) throws ValidationException {
		iAccreditedInstitute.deleteAccreditedInstitute(accreditedInstituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Deleted accredited Institute successfully").create();
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAccreditedInstituteList(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize)
			throws ValidationException {
		List<AccreditedInstituteDto> accreditedInstituteList = iAccreditedInstitute.getAccreditedInstituteList(pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(accreditedInstituteList)
				.setMessage("Get accredited Institute list successfully").create();
	}

	@GetMapping("/{accreditedInstituteId}")
	public ResponseEntity<?> getAccreditedInstituteDetail(@PathVariable final String accreditedInstituteId) throws ValidationException {
		AccreditedInstituteDto accreditedInstitute = iAccreditedInstitute.getAccreditedInstituteDetail(accreditedInstituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(accreditedInstitute).setMessage("Get accredited Institute successfully")
				.create();
	}

	@GetMapping("/getall")
	public ResponseEntity<?> getAllAccreditedInstituteDetails() {
		List<AccreditedInstituteRequestDto> accreditedInstitute = iAccreditedInstitute.getAllAccreditedInstitutes();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(accreditedInstitute).setMessage("Get accredited Institute successfully")
				.create();
	}

}
