package com.yuzee.app.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yuzee.app.confirguration.AmazonS3Configuration;
import com.yuzee.common.lib.handler.NotificationHandler;
import com.yuzee.common.lib.util.AWSUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailProcessor {

	@Autowired
	private NotificationHandler notificationHandler;
	
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
		File fileToCreate = new File(com.yuzee.app.util.IConstant.FILE_PATH + fileName);
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
		AWSUtil.writeAndDeleteFile(amazonS3Configuration.getAccessKey(), amazonS3Configuration.getBucketName(),
				amazonS3Configuration.getS3Region(), amazonS3Configuration.getSecretKey(), listOfFiles);

		return notificationHandler.sendEmailNotificationWithAttachment(emailAddress, subject, user, listOfFiles);
	}
}
