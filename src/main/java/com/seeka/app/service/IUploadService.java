package com.seeka.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

	public String importTop10Courses(MultipartFile multipartFile) throws IOException;
	
	public String importGlobalFlowOfTertiaryLevelStudents(MultipartFile multipartFile) throws IOException;
}
