package com.seeka.app.controller.v1;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.service.IUploadService;

@RestController("uploadControllerV1")
@RequestMapping("/v1")
public class UploadController {

	@Autowired
	private IUploadService uploadService;
	
	@PostMapping("/upload/top10Courses")
	public ResponseEntity<?> uploadTop10Courses(@RequestParam("top10Courses") MultipartFile multipartFile) throws IOException{
		String response = uploadService.importTop10Courses(multipartFile);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Successfully Uploaded the File").setData(response).create();
	}
	
	@PostMapping("/upload/globalFlowOfTertiaryLevelStudents")
	public ResponseEntity<?> uploadGlobalFlowOfTertiaryLevelStudents(@RequestParam("globalFlowOfTertiaryLevelStudents") MultipartFile multipartFile) throws IOException{
		String response = uploadService.importGlobalFlowOfTertiaryLevelStudents(multipartFile);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Successfully Uploaded the File").setData(response).create();
	}
}
