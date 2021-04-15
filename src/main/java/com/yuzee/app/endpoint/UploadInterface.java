package com.yuzee.app.endpoint;

import java.text.ParseException;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yuzee.app.exception.ElasticSearchInvokeException;
import com.yuzee.app.exception.IOException;
import com.yuzee.app.exception.UploaderException;

@RequestMapping("/api/v1/upload")
public interface UploadInterface {

	@PostMapping("/service")
	public ResponseEntity<?> uploadServices(@RequestParam("services") final MultipartFile multipartFile)
			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException, java.io.IOException;

	@PostMapping("/institute/institute-type")
	public ResponseEntity<?> uploadInstituteType(@RequestParam("institute_types") final MultipartFile multipartFile)
			throws IOException, JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException, java.io.IOException, ParseException;

	@PostMapping("/course")
	public ResponseEntity<?> uploadCourse(@RequestParam("courses") final MultipartFile multipartFile)
			throws IOException, UploaderException, JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException;

	@PostMapping("/course/keyword")
	public ResponseEntity<?> uploadCourseKeyword(@RequestParam("keyword") final MultipartFile multipartFile);

	@PostMapping("/institute")
	public ResponseEntity<?> uploadInstitute(@RequestParam("institutes") final MultipartFile multipartFile)
			throws IOException, UploaderException, ParseException, JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException,
			java.io.IOException;

	@PostMapping("/scholarship")
	public ResponseEntity<?> uploadScholarship(@RequestParam("scholarship") final MultipartFile multipartFile)
			throws java.io.IOException, ParseException;

	@PostMapping("/grade")
	public ResponseEntity<?> uploadGrade(@RequestParam("grade") final MultipartFile multipartFile);

	@PostMapping("/subjects")
	public ResponseEntity<Object> uploadSubject(@RequestParam("subjects") final MultipartFile multipartFile)
			throws IOException, UploaderException, ParseException, java.io.IOException;

	@PostMapping("/globalStudent")
	public ResponseEntity<Object> uploadGlobalStudentData(@RequestParam("students") MultipartFile multipartFile)
			throws java.io.IOException;

	@PostMapping("/level")
	public ResponseEntity<Object> uploadLevel(@RequestParam("level") MultipartFile multipartFile)
			throws java.io.IOException, JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException;

	@PostMapping("/faculty")
	public ResponseEntity<Object> uploadFaculty(@RequestParam("faculty") MultipartFile multipartFile)
			throws java.io.IOException, JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException;
}
