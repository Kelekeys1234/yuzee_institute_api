package com.yuzee.app.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yuzee.app.confirguration.AmazonS3Configuration;
import com.yuzee.app.dto.EmailNotificationDto;
import com.yuzee.app.dto.EmailPayloadDto;
import com.yuzee.app.dto.InstituteDto;
import com.yuzee.app.util.CommonUtil;
import com.yuzee.app.util.IConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailProcessor {

	@Autowired
	private RestTemplate restTemplate;
	
	private final String emailAddress;
	
	@Autowired
	public EmailProcessor(@Value("${emailprocessor.errorFilePath}") String errorFilePath,
			@Value("${emailprocessor.email}") String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Autowired
	private AmazonS3Configuration amazonS3Configuration;

	public void sendRemainingUploaderDataMail(List<String> rows, String fileName, String user, String subject) throws IOException{
		log.debug("Inside sendRemainingUploaderDataMail() method");
		File fileToCreate = new File(IConstant.FILE_PATH + fileName);
		try(FileOutputStream outputStream = new FileOutputStream(fileToCreate)) {
			Iterator<String> iter = rows.iterator();
			while (iter.hasNext()) {
				String outputString = iter.next();
				byte[] buffer = outputString.getBytes();
				log.info("Start writting data using output stream");
				outputStream.write(buffer);
			}
			log.info("Closing output stream");
		}

		
		List<File> listOfFiles = new ArrayList<>();
		listOfFiles.add(fileToCreate);
		
		int statusCode = sendEmailWithAttachment(subject, user, listOfFiles);
		if (statusCode == 200) {
			log.info("deleting file if status code contains is 200");
			fileToCreate.delete();
		}

	}
	
	public int sendEmailWithAttachment(String subject, String user, List<File> listOfFiles) {
		log.info("Start writing and deleting file on S3 bucket having bucketName : {}",amazonS3Configuration.getBucketName());
		CommonUtil.writeAndDeleteFile(amazonS3Configuration.getAccessKey(), amazonS3Configuration.getBucketName(),
				amazonS3Configuration.getS3Region(), amazonS3Configuration.getSecretKey(), listOfFiles);

		EmailNotificationDto emailNotificationDto = new EmailNotificationDto();
		EmailPayloadDto emailPayloadDto = new EmailPayloadDto();
        emailPayloadDto.setToaddress(emailAddress);
		emailPayloadDto.setSubject(subject);

		emailNotificationDto.setChannel("EMAIL");
		emailNotificationDto.setUser(user);
		emailNotificationDto.setUserId("uploader");
		emailNotificationDto.setNotificationType("Email");

		emailPayloadDto.setBody("Please find below Attachment");
		emailNotificationDto.setPayload(emailPayloadDto);

		log.info("Going to call nltification service to send mail");
		ResponseEntity<Object> object = restTemplate.postForEntity(
				"http://" + IConstant.NOTIFICATION + "email?attachment=" + listOfFiles, emailNotificationDto,
				Object.class);
		return object.getStatusCodeValue();
	}
	
	public void mailNewUploadedInstitutes(List<InstituteDto> finalInstitutesList) throws IOException{
		List<String> rows = new ArrayList<>();
		for (InstituteDto institute : finalInstitutesList) {
			boolean newRow = true;
			String data = "";
			log.info("if instituteName or countryName or cityName is null");
			try {
				rows.add("University_Name, Country, City, Created date");
				rows.add("\n");

				if (institute != null) {
					if (newRow && institute.getName() != null) {
						data = data + (institute.getName().replace(",", ""));
					} else {
						data = data + " ";
					}

					if (newRow && institute.getCountryName() != null) {
						data = data + "," + (institute.getCountryName());
					} else {
						data = data + "," + " ";
					}

					if (newRow && institute.getCityName() != null) {
						data = data + "," + (institute.getCityName());
					} else {
						data = data + "," + " ";
					}

					data = data + "," + (new Date());

					newRow = false;
					rows.add(data);
					data = "";
					rows.add("\n");
				}
			} catch (Exception e) {
				log.error("Exception in Saving Institute Data exception {} ", e);
			}
			newRow = true;
			rows.add(data);
			rows.add("\n");
		}
		sendRemainingUploaderDataMail(rows, "failedInstituteList.csv", "Institute Uploader", "Failed Institutes");
	}

}
