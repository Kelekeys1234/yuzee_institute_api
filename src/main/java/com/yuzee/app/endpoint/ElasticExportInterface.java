package com.yuzee.app.endpoint;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.exception.ElasticSearchInvokeException;

@RequestMapping("/api/v1/elastic/export")
public interface ElasticExportInterface {

	@PostMapping("/institute")
	public ResponseEntity<Object> exportInstitutes() throws ElasticSearchInvokeException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException;

	@PostMapping("/course")
	public ResponseEntity<Object> exportCourses() throws ElasticSearchInvokeException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException;
}
