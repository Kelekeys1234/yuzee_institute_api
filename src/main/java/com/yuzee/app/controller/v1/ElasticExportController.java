package com.yuzee.app.controller.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.ElasticExportInterface;
import com.yuzee.app.exception.ElasticSearchInvokeException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseProcessor;
import com.yuzee.app.processor.InstituteProcessor;

@RestController("elasticExportControllerV1")
public class ElasticExportController implements ElasticExportInterface {

	public static final Logger LOGGER = LoggerFactory.getLogger(ElasticExportController.class);
	
	@Autowired
	private InstituteProcessor instituteProcessor;

	@Autowired
	private CourseProcessor courseProcessor;
	
	@Override
	public ResponseEntity<Object> exportInstitutes() throws ElasticSearchInvokeException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		LOGGER.debug("exportInstitutes() start");
		LOGGER.info("Going to save institutes on elasticSearch");
		instituteProcessor.exportInstituteToElastic();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Insitutes Added successfully").create();
	}

	@Override
	public ResponseEntity<Object> exportCourses() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		LOGGER.debug("exportCourses() start");
		LOGGER.info("Going to save courses on elasticSearch");
		courseProcessor.exportCourseToElastic();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Courses Added successfully").create();
	}
}
