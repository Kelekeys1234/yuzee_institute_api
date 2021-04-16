package com.yuzee.app.controller.v1;

import java.text.ParseException;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yuzee.app.endpoint.UploadInterface;
import com.yuzee.app.processor.CourseProcessor;
import com.yuzee.app.processor.EducationSystemProcessor;
import com.yuzee.app.processor.FacultyProcessor;
import com.yuzee.app.processor.GlobalStudentProcessor;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.processor.InstituteTypeProcessor;
import com.yuzee.app.processor.LevelProcessor;
import com.yuzee.app.processor.ScholarshipProcessor;
import com.yuzee.app.processor.ServiceProcessor;
import com.yuzee.app.processor.SubjectProcessor;
import com.yuzee.common.lib.exception.IOException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController("uploadControllerV1")
@Slf4j
public class UploadController implements UploadInterface {

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private InstituteProcessor instituteProcessor;

	@Autowired
	private ScholarshipProcessor scholarshipProcessor;

	@Autowired
	private SubjectProcessor subjectProcessor;

	@Autowired
	private GlobalStudentProcessor globalStudentProcessor;

	@Autowired
	private InstituteTypeProcessor instituteTypeProcessor;

	@Autowired
	private ServiceProcessor serviceProcessor;

	@Autowired
	private FacultyProcessor facultyProcessor;

	@Autowired
	private EducationSystemProcessor educationSystemProcessor;
	
	@Autowired
	private LevelProcessor levelProcessor;

	@Override
	public ResponseEntity<Object> uploadInstituteType(final MultipartFile multipartFile)
			throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException, java.io.IOException, ParseException {
		log.info("Started process to Upload Activity");
		instituteTypeProcessor.importInstituteType(multipartFile);
		return new GenericResponseHandlers.Builder().setMessage("Institute Types Uploaded Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<Object> uploadServices(final MultipartFile multipartFile)
			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException, java.io.IOException {
		log.info("Started process to Upload Service");
		serviceProcessor.importServices(multipartFile);
		return new GenericResponseHandlers.Builder().setMessage("Services Uploaded Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<Object> uploadCourse(final MultipartFile multipartFile)
			throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
		log.info("Started process to Upload Course");
		courseProcessor.uploadCourseData(multipartFile);
		return new GenericResponseHandlers.Builder().setMessage("Courses Uploaded Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<Object> uploadCourseKeyword(final MultipartFile multipartFile) {
		log.info("Started process to Upload CourseKeyword");
		courseProcessor.importCourseKeyword(multipartFile);
		return new GenericResponseHandlers.Builder().setMessage("CourseKeywords Uploaded Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<Object> uploadInstitute(final MultipartFile multipartFile) throws 
			ParseException, JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException, java.io.IOException {
		log.info("Started process to Upload Institute");
		instituteProcessor.importInstitute(multipartFile);
		return new GenericResponseHandlers.Builder().setMessage("Institute Uploaded Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<Object> uploadScholarship(final MultipartFile multipartFile)
			throws java.io.IOException, ParseException {
		log.info("Started process to Upload Scholarship");
//		scholarshipProcessor.importScholarship(multipartFile);
		return new GenericResponseHandlers.Builder()
				.setMessage("Scholarship uploaded successfully").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<Object> uploadGrade(final MultipartFile multipartFile) {
		log.info("Started process to Upload Grade");
//		instituteProcessor.importEducationSystem(multipartFile);
//		instituteProcessor.uploadGrade(multipartFile);
		return new GenericResponseHandlers.Builder().setMessage("Grades Uploaded Successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@Override
	public ResponseEntity<Object> uploadSubject(final MultipartFile multipartFile)
			throws ParseException, java.io.IOException {
		log.info("Started process to Upload Subject");
		subjectProcessor.importSubject(multipartFile);
		return new GenericResponseHandlers.Builder().setMessage("Subjects Uploaded Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<Object> uploadGlobalStudentData(MultipartFile multipartFile) throws java.io.IOException {
		log.info("Started process to Upload Global Student");
		globalStudentProcessor.uploadGlobalStudentData(multipartFile);
		return new GenericResponseHandlers.Builder().setMessage("Students Uploaded Successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<Object> uploadLevel(MultipartFile multipartFile)
			throws java.io.IOException, JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		log.info("Started process to Upload Level");
		levelProcessor.importLevel(multipartFile);
		return new GenericResponseHandlers.Builder().setMessage("level Uploaded Successfully").setStatus(HttpStatus.OK)
				.create();
	}

	@Override
	public ResponseEntity<Object> uploadFaculty(MultipartFile multipartFile)
			throws java.io.IOException, JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		log.info("Started process to Upload Faculty");
		facultyProcessor.importFaculty(multipartFile);
		return new GenericResponseHandlers.Builder().setMessage("Faculty Uploaded Successfully")
				.setStatus(HttpStatus.OK).create();
	}

}
