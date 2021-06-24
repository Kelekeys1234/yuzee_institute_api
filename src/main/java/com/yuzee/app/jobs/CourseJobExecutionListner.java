package com.yuzee.app.jobs;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuzee.common.lib.processor.LogFileProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseJobExecutionListner implements JobExecutionListener {
	
	@Autowired
	private LogFileProcessor logFileProcessor;
	
	private String executionId;
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("Course import done.");
		logFileProcessor.sendFailureEmail(executionId, "UPLOADER: Course batch upload Failures");
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		String header = "Course Name,Institute Name,Level Name,Faculty Name,Exception";
		executionId = jobExecution.getJobParameters().getString("execution-id");
    	try {
			logFileProcessor.appendToLogFile(executionId, Arrays.asList(header));
		} catch (IOException e) {
			log.error("Exception occured {}",e);
		}
	}
}