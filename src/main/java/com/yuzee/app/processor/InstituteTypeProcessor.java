package com.yuzee.app.processor;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.common.lib.exception.NotFoundException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.yuzee.app.bean.InstituteType;
import com.yuzee.app.dto.InstituteTypeDto;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class InstituteTypeProcessor {

	@Autowired
	private InstituteDao instituteDAO;

	@Autowired
	@Qualifier("importInstituteTypeJob")
	Job job;

	@Autowired
	JobLauncher jobLauncher;	
	
	@Value("${s3.url}")
	private String s3BaseUrl;
	
	@Value("${s3.institute-type.image.location}")
	private String instituteTypeImageLocation;

	public void addUpdateInstituteType(String instituteId, String instituteType) {
		log.debug("Inside addUpdateInstituteType() method");
		Institute institute = instituteDAO.get(UUID.fromString(instituteId));
		if(ObjectUtils.isEmpty(institute)){
			log.error("no Institute Found");
			throw new NotFoundException("No institute found");
		}
		institute.setInstituteType(instituteType);
		log.info("Calling DAO layer to save instituteType in DB");
		instituteDAO.addUpdateInstitute(institute);
	}

	public InstituteType getInstituteType(String id) {
	//	return iInstituteTypeDAO.get(id);
		return null;
	}

	public List<InstituteTypeDto> getAllInstituteType() {
	//	return iInstituteTypeDAO.getAll();
		return null;
	}

	public List<InstituteTypeDto> getInstituteTypeByCountryName(String countryName) {
		log.debug("Inside getInstituteTypeByCountryName() method");
		List<InstituteType> listOfInstituteType = instituteDAO.getByCountryName(countryName);
		if(CollectionUtils.isEmpty(listOfInstituteType)){
			log.error("No InstituteType found");
			throw new NotFoundException("No instituteType found");
		}
		return listOfInstituteType.stream().map(e -> new InstituteTypeDto(e.getName(), e.getDescription(), e.getCountryName())).collect(Collectors.toList());
	}

	public void importInstituteType(final MultipartFile multipartFile) throws IOException, ParseException, JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		log.debug("Inside importInstitute() method");
		log.info("Calling methiod to save instituteData");

		File f = File.createTempFile("InstituteTypes", ".csv");
		multipartFile.transferTo(f);

		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString("csv-file", f.getAbsolutePath());
		jobParametersBuilder.addString("execution-id", "InstituteTypeUploader-" + UUID.randomUUID().toString());
		jobLauncher.run(job, jobParametersBuilder.toJobParameters());
	}

	public List<com.yuzee.common.lib.enumeration.InstituteType> getInstituteTypes() {
		return Arrays.asList(com.yuzee.common.lib.enumeration.InstituteType.values()).stream().map(e -> {
			if (!e.isInitialized()) {
				e.setIcon(createInstituteTypeImageURL(e.getIcon()));
				e.setInitialized(true);
			}
			return e;
		}).collect(Collectors.toList());
	}
	
	private String createInstituteTypeImageURL(String name) {
		return new StringBuffer().append(s3BaseUrl).append(instituteTypeImageLocation).append(name).toString();
	}

}
